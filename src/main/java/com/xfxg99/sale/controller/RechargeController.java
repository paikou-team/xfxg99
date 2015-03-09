package com.xfxg99.sale.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
   
import com.xfxg99.core.ListResult;
import com.xfxg99.sale.model.Recharge;
import com.xfxg99.sale.service.RechargeService;
import com.xfxg99.sale.viewmodel.CustomerVM;
import com.xfxg99.sale.viewmodel.RechargeVM;

@Scope("prototype")
@Controller
@RequestMapping("/charge")
public class RechargeController {

	@Resource(name = "rechargeService")
	protected RechargeService rechargeService;

	@RequestMapping(value = "getList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getList(
			@RequestParam(value = "charge_Query", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) throws Exception {

		JSONObject joQuery = JSONObject.fromObject(query); 
		String orgname = joQuery.getString("orgname"); 
		String custname = joQuery.getString("custname");
		int isConfirm = Integer.parseInt(joQuery.getString("isconfirm"));
		String confirmname = joQuery.getString("username");

		Map<String, Object> map = new HashMap<String, Object>();

		page = page == 0 ? 1 : page;
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("orgname", orgname);
		map.put("custname", custname);
		map.put("isConfirm", isConfirm);
		map.put("confirmname", confirmname);

		ListResult<RechargeVM> rs = rechargeService.loadrechargelist(map);

		return rs.toJson();
	}
	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "saveRechargeObj.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String saveRechargeObj(Recharge charge) throws Exception {
		try {  
			int result = 0;
			if (charge.getId() > 0) { 
				result = rechargeService.updateByPrimaryKey(charge);
			} else {
				charge.setId(0);
				result = rechargeService.insert(charge);
			}
			return "{\"success\":true,\"Message\":\"保存成功,result is " + result
					+ "\"}";
		} catch (Exception ex) {
			return "{\"success\":false,\"Message\":\"保存失败，原因："
					+ ex.getMessage() + "\"}";
		}
	}
	
	/*
	 * 获取注册用户列表，以下拉列表形式呈现；
	 */
	@RequestMapping(value = "getcustList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getcustList() throws Exception {
		try {
			List<CustomerVM> list = rechargeService.selectCustomerList();
			JSONArray result = JSONArray.fromObject(list);
			return result.toString();
		} catch (Exception ex) {
			return "";
		}
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