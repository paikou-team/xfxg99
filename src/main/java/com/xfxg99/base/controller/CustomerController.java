package com.xfxg99.base.controller;
 
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.BirthdayInfo;
import com.xfxg99.base.model.Organization;
import com.xfxg99.base.service.BirthdayInfoService;
import com.xfxg99.base.service.CustomerService;
import com.xfxg99.base.service.OrganizationService;
import com.xfxg99.base.viewmodel.CustomerVM;
import com.xfxg99.base.viewmodel.UserVM;
import com.xfxg99.core.GeneralUtil;
import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result; 
import com.xfxg99.core.Sms;

@Scope("prototype")
@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Resource(name = "customerService")
	protected CustomerService customerService;

	@Resource(name = "birthdayInfoService")
	protected BirthdayInfoService birthdayInfoService;
	
	@Resource(name = "organizationService")
	protected OrganizationService orgService;
	
	/*
	 * 获取注册用户列表，以下拉列表形式呈现；
	 */
	@RequestMapping(value = "getcustList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getcustList(
			@RequestParam(value = "custuser_query", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject joQuery = JSONObject.fromObject(query);
		String name = joQuery.getString("custname");
		String phone = joQuery.getString("phone");
//		String recUser = joQuery.getString("recUser");
		page = page == 0 ? 1 : page;
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("name", name);
		map.put("phone", phone);
//		map.put("orgname", orgname);
//		map.put("recUser", recUser);

		ListResult<CustomerVM> rs = customerService
				.loadrechargeCustUserlist(map);

		return rs.toJson();
	}

	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "saveCustUserObj.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String saveCustUserObj(CustomerVM customer, HttpServletRequest request)
			throws Exception {
		try {
			HttpSession session = request.getSession();
			UserVM user = (UserVM) session.getAttribute("user");
			if (user == null) {
				Result<CustomerVM> s = new Result<CustomerVM>(null, true,
						false, false, "页面过期，请重新登录");
				return s.toJson();
			} else {
				if (customer.getId() > 0) {
					customerService.updateByPrimaryKey(customer);
				} else {
					customer.setId(0);
					customerService.insert(customer);
				}
				Result<CustomerVM> s = new Result<CustomerVM>(null, true,
						false, false, "保存成功");
				return s.toJson();
			}
		} catch (Exception ex) {
			Result<CustomerVM> s = new Result<CustomerVM>(null, false, false,
					false, "调用后台方法出错");
			return s.toJson();
		}
	}
	
	/*
	 * 获取注册用户列表，以下拉列表形式呈现；
	 */
	@RequestMapping(value = "getCustomer.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getCustomer(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request) throws Exception {

		Result<CustomerVM> result=null;
		
		try{
			CustomerVM c=customerService.getCustomer(id);
			
			result=new Result<CustomerVM>(c,true,false,false,null);
			
			return result.toJson();
		}catch(Exception ex){
			result= new Result<CustomerVM>(null,false,false,false,ex.getMessage());
			return result.toJson();
		}
		
	}
	
	@RequestMapping(value = "getBirthdayInfoList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getBirthdayInfoList(
			@RequestParam(value = "birthdayInfoQuery", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request
			)throws Exception{
		
		UserVM user =(UserVM)request.getSession().getAttribute("user");
		ListResult<CustomerVM>  result =null;
		
		if(user ==null){
			result =new ListResult<CustomerVM>(0,null);
			result.setIsSessionExpired(true);
			result.setMsg("请重新登录!");
			return result.toJson();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject joQuery = JSONObject.fromObject(query);
		String custName = joQuery.getString("custName");
		String orgName = joQuery.getString("orgName");

		custName= "".equals(custName)?null:custName;
		orgName= "".equals(orgName)?null:orgName;
		
		page = page == 0 ? 1 : page;
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("custName", custName);
		map.put("orgName", orgName);
		
		Calendar c=Calendar.getInstance();
		int year =c.get(Calendar.YEAR);
		map.put("year", year);
		
		if(!user.getIsAllDataPermission()){
			Organization org=orgService.getOrganization(user.getOrgId());
			map.put("userOrgPath", org.getPath());
		}
		
		result = customerService.loadBirthdayInfoList(map);
		
		return result.toJson();
	}
	
	/*
	 * 获取注册用户列表，以下拉列表形式呈现；
	 */
	@RequestMapping(value = "getBirthdayInfo.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getBirthdayInfo(
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "year", required = false) Integer year,
			HttpServletRequest request) throws Exception {

		Result<BirthdayInfo> result=null;
		
		try{
			
			if(year==null || year ==0){
				year= Calendar.getInstance().get(Calendar.YEAR);
			}
			
			BirthdayInfo info=birthdayInfoService.loadBirthdayInfo(id, year);
			
			if(info==null){
				info=new BirthdayInfo();
				info.setBirthdayYear(year);
				info.setCustId(id);
				info.setId(0);
			}
			
			result=new Result<BirthdayInfo>(info,true,false,false,null);
			
			return result.toJson();
		}catch(Exception ex){
			result= new Result<BirthdayInfo>(null,false,false,false,ex.getMessage());
			return result.toJson();
		}
	}
	
	@RequestMapping(value = "saveBirthdayInfo.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String saveBirthdayInfo(
			@RequestParam(value = "birthdayInfo", required = false) String dataJson,
			HttpServletRequest request) throws Exception {

		Result<BirthdayInfo> result=null;

		try{
			JSONObject jObj = JSONObject.fromObject(dataJson);
			BirthdayInfo info = (BirthdayInfo) JSONObject.toBean(jObj,BirthdayInfo.class);
			
			birthdayInfoService.saveBirthdayInfo(info);
			
			result=new Result<BirthdayInfo>(info,true,false,false,null);
			
			return result.toJson();
		}catch(Exception ex){
			result= new Result<BirthdayInfo>(null,false,false,false,ex.getMessage());
			return result.toJson();
		}
	}
}
