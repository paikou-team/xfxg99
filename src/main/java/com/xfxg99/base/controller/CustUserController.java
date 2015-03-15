package com.xfxg99.base.controller;
 
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

import com.xfxg99.base.viewmodel.UserVM;
import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result; 
import com.xfxg99.sale.service.CustUserService;
import com.xfxg99.sale.viewmodel.CustomerVM;

@Scope("prototype")
@Controller
@RequestMapping("/custuser")
public class CustUserController {

	@Resource(name = "custuserService")
	protected CustUserService custuserService;

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
		String orgname = joQuery.getString("orgname");
		String recUser = joQuery.getString("recUser");
		page = page == 0 ? 1 : page;
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("name", name);
		map.put("orgname", orgname);
//		map.put("recUser", recUser);

		ListResult<CustomerVM> rs = custuserService
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
					custuserService.updateByPrimaryKey(customer);
				} else {
					customer.setId(0);
					custuserService.insert(customer);
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
}
