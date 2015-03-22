package com.xfxg99.base.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
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
						false, "登录过期，请重新登录");
				return s.toJson();
			} else {
				Result<UserVM> s = new Result<UserVM>(u, true, false, false, "");
				return s.toJson();
			}
		} catch (Exception ex) {
			Result<UserVM> s = new Result<UserVM>(null, false, false, false,
					"获取登录用户信息失败，请联系网站管理员");
			return s.toJson();
		}
	}
	
	/**
	 * 退出主页，返回登录页面
	 * @throws IOException 
	 */
	@RequestMapping(value = "onExit.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	void onExit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();//清除当前用户相关的session对象
		response.sendRedirect("../login.jsp");

	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "changePwdAction.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	void changePwdAction(
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "newPassword", required = true) String newPassword,
			@RequestParam(value = "TooNewPassword", required = true) String TooNewPassword,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// HttpSession session = req.getSession();
		HttpSession session = request.getSession();
		UserVM u = (UserVM) session.getAttribute("user");
		//判断用户是否过期（null）
		if (u == null) {
			response.sendRedirect("../index.jsp?optType=1");
		} else {
				//根据session中的user的id查出password
				User user = userService.selectByPrimaryKey(u.getId());
				String pwd = encryption(password);
				//输入与原密码是否一致
				if (!pwd.equals(user.getPassword())) {
					response.sendRedirect("../index.jsp?optType=2");
					
				} else {
					//两次输入
					if (!newPassword.equals(TooNewPassword)) {
						response.sendRedirect("../index.jsp?optType=3");
					} else {
						String nPwd = encryption(newPassword);
						
						user.setPassword(nPwd);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", user.getId());
						map.put("password", nPwd);
						userService.updatePwdByPrimaryKey(map);
						response.sendRedirect("../index.jsp?optType=4");
					}
				}
			}
		}
		
	

	@RequestMapping(value = "loginAction.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	void loginAction(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			HttpSession session = request.getSession();
			Map<String, Object> map = new HashMap<String, Object>();
			if (username.trim().length() == 0) {
				Result<UserVM> s = new Result<UserVM>(null, false, false,
						false, "用户名不能为空");
				response.sendRedirect("../login.jsp");
				// return s.toJson();
			}
			if (password.trim().length() == 0) {
				Result<UserVM> s = new Result<UserVM>(null, false, false,
						false, "密码不能为空");
				response.sendRedirect("../login.jsp");
				// return s.toJson();
			}
			String nPwd = encryption(password);
			map.put("name", username);
			map.put("pwd", nPwd);
			UserVM u = userService.loadUserByNameAndPwd(map);
			if (u != null) {
				if (u.getIsUsed() == false) {
					Result<UserVM> s = new Result<UserVM>(null, false, false,
							false, "用户已停用");
					response.sendRedirect("../login.jsp");
				}
				session.setAttribute("user", u);
				Result<UserVM> s = new Result<UserVM>(u, true, false, false,
						"用户登录验证成功");
				response.sendRedirect("../index.jsp");
			} else {
				Result<UserVM> s = new Result<UserVM>(u, false, false, false,
						"用户登录验证失败，账号或者密码错误，请检查");
				response.sendRedirect("../login.jsp");
			}
		} catch (Exception ex) {
			Result<UserVM> s = new Result<UserVM>(null, false, false, false,
					"用户登录验证失败，请联系网站管理员");
			response.sendRedirect("../login.jsp");
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

	/**
	 * 
	 * @param plainText
	 *            明文
	 * @return 32位密文
	 */
	public String encryption(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}
}
