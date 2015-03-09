package com.xfxg99.base.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.User;
import com.xfxg99.base.service.UserService;
import com.xfxg99.core.ListResult;

import com.xfxg99.base.service.AuthorizeService;

/**
 * �û�
 * @author Sam
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController {

	@Resource(name = "userService")
	protected UserService userService;
	@Resource(name = "authorizeService")
	protected AuthorizeService authorizeService;
	
	Integer mUserId = 0;
	
	@RequestMapping(value = "getList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getList(HttpServletRequest request){
		
		List<User> ls=userService.loadAllUser();
		
		ListResult<User> funcs=new ListResult<User>(ls.size(),ls,true);
		
		return funcs.toJson();
	}
	@RequestMapping(value = "saveUser.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Integer  saveUser(User user)
	{
		int result = userService.saveUser(user);
		if(result == 0)
		{
			 return 0;
		}
		Integer userId = user.getId();
	
		return userId;
	}
	
	@RequestMapping(value = "deleteUser.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody boolean  deleteUser(
			@RequestParam(value = "Id", required = true) Integer id,
			HttpServletRequest request){
		int result = userService.deleteUser(id);
		if(result == 0)
		{
			 return false;
		}
		return true;
	}
	
	@RequestMapping(value = "saveAuthorize.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody boolean  saveAuthorize(
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "ids", required = true) String ids,
			HttpServletRequest request)	{
		authorizeService.deleteByUserId(userId);
		String[]  destString = ids.split(","); 
		for(int i=0; i < destString.length; i++) 
		{
			Integer funId=new Integer(destString[i]); 
			authorizeService.insert(userId,funId);
		}
		return true;
	}
	
}
