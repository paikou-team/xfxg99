package com.xfxg99.base.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.User;
import com.xfxg99.base.service.UserService;
import com.xfxg99.core.ListResult;

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
	
	@RequestMapping(value = "getList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getList(HttpServletRequest request){
		
		List<User> ls=userService.loadAllUser();
		
		ListResult<User> funcs=new ListResult<User>(ls.size(),ls,true);
		
		return funcs.toJson();
	}
	@RequestMapping(value = "deleteUser.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody void  deleteUser(Integer id){
		int result = userService.deleteUser(id);
		if(result == 0)
		{
			 JOptionPane.showMessageDialog(null, "删除记录失败！", "消息提示", JOptionPane.ERROR_MESSAGE);
			 return;
		}
		JOptionPane.showMessageDialog(null, "删除记录成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
