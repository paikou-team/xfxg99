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
	
	@RequestMapping(value = "getSortList.do")
	public @ResponseBody String getSortList(HttpServletRequest request){
		
		String data = GetTreeGridNode(0);
		
        String test = "[" + data + "]";
        return test;
	}
	private String GetTreeGridNode(Integer parentId)
    {
		List<Organization> list = organizationService.getParentIdItems(parentId);
  
		StringBuilder child = new StringBuilder();
        for (Organization item : list)
        {
            child.append("{");
            child.append("\"id\":\"");
            child.append(item.getId());
            child.append("\",");
            //child.AppendFormat("\"Id\":\"{0}\",", item.getId());
            child.append("\"name\":\"");
            child.append(item.getName());
            child.append("\",");
            //child.AppendFormat("\"Name\":\"{0}\",", item.getName());
            child.append("\"path\":\"");
            child.append(item.getPath());
            child.append("\",");
           // child.AppendFormat("\"path\":\"{0}\",", item.getPath());
            
            Integer tmpId = item.getParentId();
            child.append("\"parentId\":\"");
            child.append(item.getParentId());
            child.append("\",");
            if(tmpId >0)
            {
            	Organization parentorg = organizationService.getOrganization(tmpId);
            	if(parentorg != null )
            	{
            		child.append("\"parentName\":\"");
                    child.append(parentorg.getName());
                    child.append("\",");
            	}
            }
            //child.AppendFormat("\"parentId\":\"{0}\",", item.getParentId());
            child.append("\"level\":\"");
            child.append(item.getLevel());
            child.append("\",");
            //child.AppendFormat("\"level\":\"{0}\",", item.getLevel());
            child.append("\"address\":\"");
            child.append(item.getAddress());
            child.append("\"");
            //child.AppendFormat("\"address\":\"{0}\"", item.getAddress());

            String childNode = GetTreeGridNode(item.getId());
            if (!childNode.isEmpty())
            {
                child.append(",\"children\":[");
                child.append(GetTreeGridNode(item.getId()));
                child.append("]");
            	//child.AppendFormat(",\"children\":[{0}]", GetTreeGridNode(item.getId()));
            }
            child.append("},");
        }
        if (child.length() > 0)
        {
            child = child.replace(child.length() - 1, child.length(), "");
        }
        return child.toString();
    }
	
	@RequestMapping(value = "GetOrganizationData.do")
	public String GetOrganizationData(HttpServletRequest request)
    {
        List<Organization> ls=organizationService.loadAllOrganization();
        StringBuilder child = new StringBuilder();
        try
        {
            if (ls.size() > 0)
            {
            	child.append("{\"total\":\"");
            	child.append(ls.size());
            	child.append("\",\"rows\":[");
            	//child.append("[");
            	for (Organization item:ls)
                {
                	child.append("{");
                    child.append("\"id\":\"");
                    child.append(item.getId());
                    child.append("\",");
                    
                    child.append("\"text\":\"");
                    child.append(item.getName());
                    child.append("\"},");
//                    eList.Add(new { id = ls.get(i).getId(), text = ls.get(i).getName() });
                }
                child = child.replace(child.length() - 1, child.length(), "]");
                child.append("}");
            }
            
        }
        catch (Exception ex) { }
        return child.toString();
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
