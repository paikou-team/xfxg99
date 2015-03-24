package com.xfxg99.base.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import com.xfxg99.base.model.User;
import com.xfxg99.base.service.UserService;
import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result; 
import com.xfxg99.sale.viewmodel.StockBillVM;
import com.xfxg99.base.viewmodel.UserVM;
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
	public @ResponseBody
	String getList(
			@RequestParam(value = "userQuery", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) throws Exception {

		JSONObject joQuery = JSONObject.fromObject(query); 
		//String userName = joQuery.getString("userName");
		String userName=null;
		if(joQuery.containsKey("userName")){
			userName = joQuery.getString("userName").trim();
		}
		if(userName == "null" || userName.length()==0){
			userName=null;
		}

		Map<String, Object> map = new HashMap<String, Object>();

		page = page == 0 ? 1 : page;
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("userName", userName);

		ListResult<UserVM> rs = userService.loadUserlist(map);

		return rs.toJson();
	}
	
	@RequestMapping(value = "saveUser.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody Integer  saveUser(User user,
			HttpServletRequest request)
	{
		if(user == null)
		{
			return 0;
		}
		if(user.getId() == 0)
		{
			String userPw = user.getPassword();
			String md5Pw = encryption(userPw);
			user.setPassword(md5Pw);
		}

		int result = userService.saveUser(user);
		if(result == 0)
		{
			 return 0;
		}
		if(user.getId()>0)
		{
			HttpSession session = request.getSession();
			UserVM currentUser = (UserVM)session.getAttribute("user");
			if(currentUser != null && currentUser.getId() ==  user.getId())
			{
				session.setAttribute("user", user);  
			}
		}
		
		
		Integer userId = user.getId();
	
		return userId;
	}
	
	@RequestMapping(value = "updateUserPassword.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody boolean  updateUserPassword(User user,
			HttpServletRequest request)
	{
		if(user == null || user.getId() == 0)
		{
			return false;
		}

		String userPw = user.getPassword();
		String md5Pw = encryption(userPw);
		user.setPassword(md5Pw);
		
		int result = userService.saveUser(user);
		if(result == 0)
		{
			 return false;
		}
	
		return true;
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
	@RequestMapping(value = "getCurrentUser.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String  getLogUser(HttpServletRequest request)
	{
		try{
			HttpSession session = request.getSession();
			UserVM user = (UserVM)session.getAttribute("user");
			
			String message="";
			boolean isSessionExpired = false;
			boolean isSuccess = true;
			if(user == null)
			{
				isSessionExpired = true;
				isSuccess = false;
				message = "Session过期，请重新登录";
			}
			Result<UserVM> s = new Result<UserVM>(user, isSuccess,
					isSessionExpired, false, message);
			return s.toJson();
		} catch (Exception ex) {
			String message = "Session过期，请重新登录";
			Result<UserVM> s = new Result<UserVM>(null, false, true,
					false, message);
			return s.toJson();
		}
	}
	
	@RequestMapping(value = "checkAuthorize.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String  checkAuthorize(
			@RequestParam(value = "funcKey", required = true) String funcKey,
			HttpServletRequest request)
	{
		User user =(User)request.getSession().getAttribute("user");
		Result<Boolean>  result =null;
		
		if(user ==null){
			result =new Result<Boolean>(null,false,true,false,"请从新登录");
			return result.toJson();
		}
		
		Boolean b=userService.checkAuthorize(user.getId(), funcKey);
		result =new Result<Boolean>(b,true,false,false,null);
		return result.toJson();
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
   
   @RequestMapping(value = "saveUserAndAuthorize.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String  saveUserAndAuthorize(
			User user,
			@RequestParam(value = "ids", required = true) String ids,
			HttpServletRequest request)
	{
	   	UserVM cuser =(UserVM)request.getSession().getAttribute("user");
		Result<User>  result =null;

		if(cuser ==null){
			result =new Result<User>(null,false,true,false,"请从新登录");
			return result.toJson();
		}
		
		if(user == null)
		{
			result =new Result<User>(null,false,true,false,"用户信息错误");
			return result.toJson();
		}
		
		if(user.getId() == 0)
		{
			String userPw = user.getPassword();
			String md5Pw = encryption(userPw);
			user.setPassword(md5Pw);
		}
		
		int flag = userService.saveUser(user);
		if(flag == 0)
		{
			result =new Result<User>(null,false,true,false,"用户保存失败");
			return result.toJson();
		}
		if(user.getId() == 0)
		{
			result =new Result<User>(null,false,true,false,"用户保存失败");
			return result.toJson();
		}
		if( cuser.getId() ==  user.getId())
		{
			request.getSession().setAttribute("user", user);  
		}
		
		authorizeService.deleteByUserId(user.getId());
		if(ids.length()>0)
		{
			String[]  destString = ids.split(","); 
			for(int i=0; i < destString.length; i++) 
			{
				Integer funId=new Integer(destString[i]); 
				authorizeService.insert(user.getId(),funId);
			}
		}
		
		result =new Result<User>(user,true,false,false,null);
	
		return result.toJson();
	}

}
