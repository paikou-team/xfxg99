package com.xfxg99.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.SysFunction;
import com.xfxg99.base.model.User;
import com.xfxg99.base.service.SysFunctionService;
import com.xfxg99.base.service.UserService;
import com.xfxg99.base.viewmodel.UserVM;
import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result;
import com.xfxg99.sale.model.Recharge;
import com.xfxg99.sale.viewmodel.StockBillVM;

/**
 * ��ҳ
 * 
 * @author Owen
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/index")
public class IndexController {

	@Resource(name = "sysFunctionService")
	protected SysFunctionService sysFunctionService;
	@Resource(name = "userService")
	protected UserService userService;

	@RequestMapping(value = "getList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getList(HttpServletRequest request) {

		List<SysFunction> ls = sysFunctionService.loadMainMenu();

		ListResult<SysFunction> funcs = new ListResult<SysFunction>(ls.size(),
				ls, true);

		return funcs.toJson();
	}

	@RequestMapping(value = "isSignIn.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String isSignIn(HttpServletRequest request) throws Exception {
		try {
			HttpSession session = request.getSession();
			UserVM u = (UserVM) session.getAttribute("user");
			if (u == null) {
				Result<UserVM> s = new Result<UserVM>(null, false, false,
						false, "");
				return s.toJson();
			} else {
				Result<UserVM> s = new Result<UserVM>(u, true, false, false, "");
				return s.toJson();
			}
		} catch (Exception ex) {
			Result<UserVM> s = new Result<UserVM>(null, false, false, false,
					"用户登录验证失败，请联系网站管理员");
			return s.toJson();
		}
	}

	@RequestMapping(value = "loginAction.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loginAction(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String passowrd,
			HttpServletRequest request) throws Exception {
		try {
			HttpSession session = request.getSession();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", username);
			map.put("pwd", passowrd);
			UserVM u = userService.loadUserByNameAndPwd(map);
			if (u != null) {
				session.setAttribute("user", u);
				Result<UserVM> s = new Result<UserVM>(u, true, false, false,
						"用户登录验证失败，未注册用户");
				return s.toJson();
			} else {
				Result<UserVM> s = new Result<UserVM>(u, false, false, false,
						"用户登录验证失败，账号或者密码错误，请检查");
				return s.toJson();
			}
		} catch (Exception ex) {
			Result<UserVM> s = new Result<UserVM>(null, false, false, false,
					"用户登录验证失败，请联系网站管理员");
			return s.toJson();
		}
	}

	@RequestMapping(value = "loginOff.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loginOff(HttpServletRequest request) throws Exception {
		try {
			HttpSession session = request.getSession();
			UserVM u = (UserVM) session.getAttribute("user");
			if (u != null) {
				session.removeAttribute("user");
				session.invalidate();
			}
			Result<UserVM> s = new Result<UserVM>(null, true, false, false, "");
			return s.toJson();
		} catch (Exception ex) {
			Result<UserVM> s = new Result<UserVM>(null, false, false, false,
					"用户登录验证失败，请联系网站管理员");
			return s.toJson();
		}
	}
}
