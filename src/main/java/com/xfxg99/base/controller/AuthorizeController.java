package com.xfxg99.base.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.Authorize;
import com.xfxg99.base.service.AuthorizeService;
import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result;

/**
 *
 * @author sam
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/authorize")
public class AuthorizeController {

	@Resource(name = "authorizeService")
	protected AuthorizeService authorizeService;
	
	@RequestMapping(value = "getListByUserId.do",produces = "application/json;charset=UTF-8")
	public @ResponseBody String getListByUserId(@RequestParam(value = "userId", required = true) Integer userId,
			HttpServletRequest request){
		
		List<Authorize> ls=authorizeService.getFunctionByUserId(userId);
		
		ListResult<Authorize> funcs=new ListResult<Authorize>(ls.size(),ls,true);
		
		return funcs.toJson();
	}
	
	@RequestMapping(value = "insert.do",produces = "application/json;charset=UTF-8")
	public @ResponseBody int insert(Authorize record)
	{
		return authorizeService.insert(record);
	}
	
	@RequestMapping(value = "deleteListByUserId.do",produces = "application/json;charset=UTF-8")
	public @ResponseBody int deleteListByUserId(@RequestParam(value = "userId", required = true) Integer userId,
			HttpServletRequest request)
	{
		return authorizeService.deleteByUserId(userId);
	}
	
	@RequestMapping(value = "isAuthorize.do",produces = "application/json;charset=UTF-8")
	public @ResponseBody String isAuthorize (
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "key", required = true) String key,
			HttpServletRequest request)
	{
		Result<Boolean> result=new Result<Boolean>();
		
		Boolean b=authorizeService.isAuthorize( key,userId);
		
		result.setData(b);
		result.setIsSuccess(true);
		
		return result.toJson();
	}
}
