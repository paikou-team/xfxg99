package com.xfxg99.base.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.dao.OrganizationMapper;
import com.xfxg99.base.model.Organization;
import com.xfxg99.base.model.User;

/**
 * ϵ�y����
 * @author Sam
 *
 */
@Service("organizationService")
public class OrganizationService{
	
	@Resource(name="organizationMapper")
	private OrganizationMapper organizationMapper;
	
	 public  List<Organization>  loadAllOrganization(){
		 
		 return organizationMapper.loadAllOrganization();
		 
	 }
	 
	 public List<Organization> getParentIdItems(Integer id){
		 
		 return organizationMapper.getParentIdItems(id);
	 }
	 
	 public int deleteOrg(Integer id){

		 return organizationMapper.deleteByPrimaryKey(id);
	 }
	 
	 public int saveOrganization(Organization org){
		 if(org != null)
		 {
			 if(org.getId()>0)
			 {
				 return organizationMapper.updateByPrimaryKeySelective(org);
			 }
			 else
			 {
				 return organizationMapper.insert(org);
			 }
		 }
		 return 0;
	 }
	 public Integer getMaxId(){
		 
		 return organizationMapper.getMaxId();
	 }
	 
	 public Organization getOrganization(Integer id){
		 
		 return organizationMapper.selectByPrimaryKey(id);
	 }
}
