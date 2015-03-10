package com.xfxg99.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.SysFunction;
import com.xfxg99.base.model.User;
import com.xfxg99.base.service.SysFunctionService;
import com.xfxg99.base.service.UserService;
import com.xfxg99.core.ListResult;

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

	@RequestMapping(value = "loginAction.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean loginAction(User user) throws Exception {
		try {
			String name = user.getName();
			String pwd = user.getPassword();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("pwd", pwd);
			User u = userService.loadUserByNameAndPwd(map);
			//HttpSession.setAttribute("user",u);
			if (u != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

}
