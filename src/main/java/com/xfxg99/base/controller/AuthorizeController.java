package com.xfxg99.base.controller;

import java.util.List;

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
	public @ResponseBody String getList(@RequestParam(value = "userId", required = true) Integer userId,
			HttpServletRequest request){
		
		List<Authorize> ls=authorizeService.getFunctionByUserId(userId);
		
		ListResult<Authorize> funcs=new ListResult<Authorize>(ls.size(),ls,true);
		
		return funcs.toJson();
	}
	
}
