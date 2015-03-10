package com.xfxg99.base.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.Organization;
import com.xfxg99.base.model.User;
import com.xfxg99.base.service.OrganizationService;
import com.xfxg99.core.ListResult;

/**
 * �û�
 * @author Sam
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/organization")
public class OrganizationController {

	@Resource(name = "organizationService")
	protected OrganizationService organizationService;
	List<Integer> mDeletelist = new ArrayList<Integer>();
	
	List<Organization> mLoadOrganization = new ArrayList<Organization>();
	
	@RequestMapping(value = "getList.do")
	public @ResponseBody String getList(HttpServletRequest request){
		
		List<Organization> ls=organizationService.loadAllOrganization();
		
		ListResult<Organization> funcs=new ListResult<Organization>(ls.size(),ls,true);
		
		return funcs.toJson();
	}
	
	@RequestMapping(value = "getParentIdItems.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String  getParentIdItems(
			@RequestParam(value = "id", required = true) Integer id,
			HttpServletRequest request){
		
		List<Organization> ls = organizationService.getParentIdItems(id);
		
		ListResult<Organization> funcs=new ListResult<Organization>(ls.size(),ls,true);
		
		return funcs.toJson();
	}
		
	@RequestMapping(value = "saveOrganization.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody boolean  saveOrganization(Organization organization)
	{
		boolean newId = false;
		int result = 0;
		if(organization != null)
		{
			if(organization.getId() == 0)
			{
				Integer level = organization.getLevel();
				organization.setLevel(level+1);
				newId = true;
			}
			result = organizationService.saveOrganization(organization);
			if(result !=0)
			{
				if(newId)
				{
					Integer id = organizationService.getMaxId();
					organization.setId(id);
	                String path = organization.getPath();
	                organization.setPath(path + id);
	                result = organizationService.saveOrganization(organization);
				}
			}
		}
		if(result == 0)
		{
			 return false;
		}
		return true;
	}
	
	
	@RequestMapping(value = "deleteOrg.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody boolean  deleteOrg(
			@RequestParam(value = "Id", required = true) Integer id,
			HttpServletRequest request){
		mDeletelist.clear();
		mDeletelist.add(id);
		GetTreeGridNodeId(id);
		for(Integer delId:mDeletelist)
		{
			organizationService.deleteOrg(delId);
		}
		return true;
	}
	
	private void GetTreeGridNodeId(Integer parentId)
    {
		List<Organization> ls = organizationService.getParentIdItems(parentId);
		for(Organization item : ls)
        {
            mDeletelist.add(item.getId());
            GetTreeGridNodeId(item.getId());
        }
    }
	
}
