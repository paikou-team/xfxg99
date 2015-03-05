package com.xfxg99.base.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.SysFunction;
import com.xfxg99.base.service.SysFunctionService;
import com.xfxg99.core.ListResult;

/**
 * Ê×Ò³
 * @author Owen
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/index")
public class IndexController {

	@Resource(name = "sysFunctionService")
	protected SysFunctionService sysFunctionService;
	
	@RequestMapping(value = "getList.do")
	public @ResponseBody String getList(HttpServletRequest request){
		
		List<SysFunction> ls=sysFunctionService.loadMainMenu();
		
		ListResult<SysFunction> funcs=new ListResult<SysFunction>(ls.size(),ls,true);
		
		return funcs.toJson();
	}
	
}
