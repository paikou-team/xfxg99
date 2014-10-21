package com.xfxg99.sale.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.Organization;
import com.xfxg99.base.service.OrganizationService;
import com.xfxg99.base.viewmodel.CustomerVM;
import com.xfxg99.base.viewmodel.UserVM;
import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result;
import com.xfxg99.sale.model.Recharge; 
import com.xfxg99.sale.service.RechargeService;
import com.xfxg99.sale.viewmodel.RechargeVM;
import com.xfxg99.sale.viewmodel.StockBillVM;

@Scope("prototype")
@Controller
@RequestMapping("/charge")
public class RechargeController {

	@Resource(name = "rechargeService")
	protected RechargeService rechargeService; 

	@Resource(name = "organizationService")
	protected OrganizationService orgService;
	
	@RequestMapping(value = "getList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getList(
			@RequestParam(value = "charge_Query", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) throws Exception {

		ListResult<RechargeVM> result=new ListResult<RechargeVM>();
		
		UserVM user =(UserVM)request.getSession().getAttribute("user");
		
		if(user ==null){
			result.setIsSuccess(false);
			result.setMsg("请重新登录");
			return result.toJson();
		}
		
		
		JSONObject joQuery = JSONObject.fromObject(query);
		String orgname = joQuery.getString("orgname");
		String custname = joQuery.getString("custname");
		int isConfirm = Integer.parseInt(joQuery.getString("isconfirm"));
		//String confirmname = joQuery.getString("username");

		Map<String, Object> map = new HashMap<String, Object>();

		page = page == 0 ? 1 : page;
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("orgname", orgname);
		map.put("custname", custname);
		map.put("isConfirm", isConfirm);
		if(!user.getIsAllDataPermission()){
			Organization org=orgService.getOrganization(user.getOrgId());
			map.put("userOrgPath", org.getPath());
		}
		//map.put("confirmname", confirmname);

		result = rechargeService.loadrechargelist(map);

		return result.toJson();
	}

	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "saveRechargeObj.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String saveRechargeObj(Recharge charge, HttpServletRequest request)
			throws Exception {
		try {
			HttpSession session = request.getSession();
			UserVM user = (UserVM) session.getAttribute("user");
			if (user == null) {
				Result<Recharge> s = new Result<Recharge>(null, true, false,
						false, "页面过期，请重新登录");
				return s.toJson();
			} else {
				if (charge.getId() > 0) {
					rechargeService.updateByPrimaryKey(charge);
				} else {
					charge.setId(0);
					rechargeService.insert(charge);
				}
				Calendar cal = Calendar.getInstance();
				String changeTime = Long.toString(cal.getTimeInMillis());
				if (changeTime.length() > 10) {
					changeTime = changeTime.substring(0, 10);
				}

				int custId = charge.getCustId();
				int orgId = charge.getOrgId();
				
				
				Map<String, Object> maps = new HashMap<String, Object>();
				maps.put("userId", custId);
				maps.put("orgId", orgId);
				
				rechargeService.updateOrgIdByPrimaryKey(maps);

				// String s = now.getTime();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", user.getId());
				map.put("custId", charge.getCustId());
				map.put("userMoney", charge.getMoney());
				map.put("frozenMoney", 0.00);
				map.put("rankPoints", 0);
				map.put("payPoints", 0);
				map.put("changeTime", changeTime);
				map.put("changeDesc", "用户充值，充值金额" + charge.getMoney());
				map.put("changeType", 2);
				rechargeService.saveRecharge(map);

				Result<Recharge> s = new Result<Recharge>(null, true, false,
						false, "保存成功");
				return s.toJson();
			}
		} catch (Exception ex) {
			Result<Recharge> s = new Result<Recharge>(null, false, false,
					false, "调用后台方法出错");
			return s.toJson();
		}
	}

	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "confirmRecharge.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String confirmRecharge(
			@RequestParam(value = "id", required = true) Integer id,
			HttpServletRequest request) throws Exception {
		try {
			Recharge charge = new Recharge();
			HttpSession session = request.getSession();
			UserVM user = (UserVM) session.getAttribute("user");
			String message = "";
			boolean isSessionExpired = false;
			boolean isSuccess = false;
			boolean isTimeout = false;
			if (user != null) {
				charge = rechargeService.selectByPrimaryKey(id);
				if (charge != null) {
					Date now = new Date();
					Calendar cal = Calendar.getInstance();
					now = cal.getTime();
					charge.setConfirmUserId(user.getId());
					charge.setConfirmTime(now);
					rechargeService.updateByPrimaryKey(charge);
					isSuccess = true;
					message = "充值确认，成功";
				} else {
					isTimeout = true;
					message = "获取数据失败";
				}
			} else {
				isSessionExpired = true;
				message = "Session过期，请重新登录";
			}
			Result<Recharge> s = new Result<Recharge>(charge, isSuccess,
					isSessionExpired, isTimeout, message);
			return s.toJson();
		} catch (Exception ex) {
			Result<Recharge> s = new Result<Recharge>(null, false, false,
					false, "调用后台方法出错");
			return s.toJson();
		}
	}

	/*
	 * 获取注册用户列表，以下拉列表形式呈现；
	 */
	@RequestMapping(value = "getcustList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getcustList(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		page = page == 0 ? 1 : page;
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("name", name);
		map.put("phone", phone);

		ListResult<CustomerVM> rs = rechargeService
				.loadrechargeCustUserlist(map);

		return rs.toJson();
	}

	/*
	 * 获取注册用户列表，以下拉列表形式呈现；
	 */
	@RequestMapping(value = "getorganList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getorganList() throws Exception {
		try {
			List<CustomerVM> list = rechargeService.selectOrganList();
			JSONArray result = JSONArray.fromObject(list);
			return result.toString();
		} catch (Exception ex) {
			return "";
		}
	}
}