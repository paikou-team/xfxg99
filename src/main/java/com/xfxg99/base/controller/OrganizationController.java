package com.xfxg99.base.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.Organization;
import com.xfxg99.base.service.OrganizationService;
import com.xfxg99.core.ListResult;

/**
 * ”√ªß
 * @author Sam
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/organization")
public class OrganizationController {

	@Resource(name = "organizationService")
	protected OrganizationService organizationService;
	
	@RequestMapping(value = "getList.do")
	public @ResponseBody String getList(HttpServletRequest request){
		
		List<Organization> ls=organizationService.loadAllOrganization();
		
		ListResult<Organization> funcs=new ListResult<Organization>(ls.size(),ls,true);
		
		return funcs.toJson();
	}
	
}
