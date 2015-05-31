package com.xfxg99.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.Organization;
import com.xfxg99.base.service.OrganizationService;
import com.xfxg99.base.service.UserService;
import com.xfxg99.base.viewmodel.OrganizationVM;
import com.xfxg99.core.ListResult;

/**
 * �û�
 * 
 * @author Sam
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/organization")
public class OrganizationController {

	@Resource(name = "organizationService")
	protected OrganizationService organizationService;

	@Resource(name = "userService")
	protected UserService userService;

	List<Integer> mDeletelist = new ArrayList<Integer>();

	List<Organization> mLoadOrganization = new ArrayList<Organization>();

	@RequestMapping(value = "getList.do")
	public @ResponseBody
	String getList(HttpServletRequest request) {

		List<Organization> ls = organizationService.loadAllOrganization();

		ListResult<Organization> funcs = new ListResult<Organization>(
				ls.size(), ls, true);

		return funcs.toJson();
	}

	@RequestMapping(value = "getOrgListById.do")
	public @ResponseBody
	String getOrgListById(
			@RequestParam(value = "orgId", required = false) Integer orgId,
			HttpServletRequest request) {
		Organization og = new Organization();
		og = organizationService.getOrganization(orgId);
		Map<String, Object> map = new HashMap<String, Object>();
		if (og != null) {
			map.put("orgId", orgId);
			map.put("orgPath", og.getPath());
		}
		List<Organization> ls = organizationService.loadOrganizationById(map);

		ListResult<Organization> funcs = new ListResult<Organization>(
				ls.size(), ls, true);

		return funcs.toJson();
	}

	@RequestMapping(value = "getParentIdItems.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getParentIdItems(
			@RequestParam(value = "id", required = true) Integer id,
			HttpServletRequest request) {

		List<Organization> ls = organizationService.getParentIdItems(id);

		ListResult<Organization> funcs = new ListResult<Organization>(
				ls.size(), ls, true);

		return funcs.toJson();
	}

	@RequestMapping(value = "saveOrganization.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean saveOrganization(Organization organization) {
		boolean newId = false;
		int result = 0;
		if (organization != null) {
			if (organization.getId() == 0) {
				Integer level = organization.getLevel();
				organization.setLevel(level + 1);
				newId = true;
			}
			result = organizationService.saveOrganization(organization);
			if (result != 0) {
				if (newId) {
					String path = organization.getPath();
					organization.setPath(path + organization.getId());
					result = organizationService.saveOrganization(organization);
				}
			}
		}
		if (result == 0) {
			return false;
		}
		return true;
	}

	@RequestMapping(value = "loadStockOrgList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadStockOrgList(HttpServletRequest request) {

		List<Organization> ls = organizationService.loadStockOrg();

		ListResult<Organization> result = new ListResult<Organization>(
				ls.size(), ls, true);

		return result.toJson();
	}

	@RequestMapping(value = "deleteOrg.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean deleteOrg(@RequestParam(value = "Id", required = true) Integer id,
			HttpServletRequest request) {
		mDeletelist.clear();
		mDeletelist.add(id);
		GetTreeGridNodeId(id);

		// 判断部门是否有用户在使用，则不能删除
		for (Integer delId : mDeletelist) {
			int userCount = userService.getUsedOrgIdCount(delId);
			if (userCount > 0) {
				return false;
			}
		}
		for (Integer delId : mDeletelist) {
			organizationService.deleteOrg(delId);
		}
		return true;
	}

	private void GetTreeGridNodeId(Integer parentId) {
		List<Organization> ls = organizationService.getParentIdItems(parentId);
		for (Organization item : ls) {
			mDeletelist.add(item.getId());
			GetTreeGridNodeId(item.getId());
		}
	}

	@RequestMapping(value = "getTreeListByParentId.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getTreeListByParentId(
			@RequestParam(value = "id", required = false) Integer hybrid_id,
			@RequestParam(value = "parentid", required = true) Integer parentid,
			HttpServletRequest request) {
		Integer qid = null;  
		List<OrganizationVM> ovm = new ArrayList<OrganizationVM>();
		List<Organization> ls =new ArrayList<Organization>();
		if (hybrid_id != null) {
			qid = hybrid_id;  
			ls = organizationService.getParentIdItems(qid); 
			ovm = getSubItems(ls,qid);
		} else {
			qid = parentid;
			ls = organizationService.getItemsByParentId(qid);
			ovm = getItems(ls,qid);
		}  
		JSONArray  json = JSONArray.fromObject(ovm);
		String resutl  = json.toString();
		return resutl;
	}

	private List<OrganizationVM> getSubItems(List<Organization> list, Integer pid) {
		// TODO Auto-generated method stub
		List<OrganizationVM> olist = new ArrayList<OrganizationVM>();
		for(Organization o:list){  
			OrganizationVM ovm = new OrganizationVM();
				if(o.getParentId() == pid){
					ovm.setId(o.getId());
					ovm.setName(o.getName());
					ovm.setText(o.getName());
					List<OrganizationVM> ls = getItemByParentId(list,o.getId());
					if(ls.size()>0){ 
						ovm.setChildren(ls);
						ovm.setState("closed");
					}else{
						ovm.setChildren(null);
						ovm.setState("open");
					}
					olist.add(ovm);
				} 
		}
		return olist;
	}

	private List<OrganizationVM> getItems(List<Organization> list,Integer pid) {
		// TODO Auto-generated method stub
		List<OrganizationVM> olist = new ArrayList<OrganizationVM>();
		for(Organization o:list){  
			OrganizationVM ovm = new OrganizationVM();
				if(o.getId() == pid){
					ovm.setId(pid);
					ovm.setName(o.getName());
					ovm.setText(o.getName());
					List<OrganizationVM> ls = getItemByParentId(list,o.getId());
					if(ls.size()>0){ 
						ovm.setChildren(ls);
						ovm.setState("open");
					}else{
						ovm.setChildren(null);
						ovm.setState("closed");
					}
					olist.add(ovm);
				} 
		}
		return olist;
	}

	private List<OrganizationVM> getItemByParentId(List<Organization> list,
			Integer id) {
		// TODO Auto-generated method stub
		List<OrganizationVM> ls = new ArrayList<OrganizationVM>();
		for(Organization o:list){
			if(o.getParentId()==id){
				OrganizationVM ovm = new OrganizationVM();
				ovm.setId(o.getId());
				ovm.setName(o.getName());
				ovm.setText(o.getName());
				ovm.setState(getChildState(o.getId()));
				ls.add(ovm);
			}
		}
		return ls;
	}

	private String getChildState(Integer parentId) {
		// TODO Auto-generated method stub
		List<Organization> s = organizationService.getParentIdItems(parentId);
		if(s.size()>0){
			return "closed";
		}else{ 
			return "open";
		}
	}
}
