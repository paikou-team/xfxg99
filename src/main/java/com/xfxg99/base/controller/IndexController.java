package com.xfxg99.base.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

	@RequestMapping(value = "loginAction.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	void loginAction(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			Map<String, Object> map = new HashMap<String, Object>();
			if(username.trim().length()==0){
				Result<UserVM> s = new Result<UserVM>(null, false, false, false,
						"用户名不能为空");
				response.sendRedirect("../login.jsp");
				//return s.toJson();
			}
			if(password.trim().length()==0){
				Result<UserVM> s = new Result<UserVM>(null, false, false, false,
						"密码不能为空");
				response.sendRedirect("../login.jsp");
				//return s.toJson();
			}
			String nPwd = encryption(password);
			map.put("name", username);
			map.put("pwd", nPwd);
			UserVM u = userService.loadUserByNameAndPwd(map);
			if (u != null) {
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
