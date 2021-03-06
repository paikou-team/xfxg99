package com.xfxg99.base.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xfxg99.base.dao.OrganizationMapper;
import com.xfxg99.base.model.Organization;

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
	 
	 public List<Organization> loadStockOrg(){
		 
		 return organizationMapper.loadStockOrg();
	 }

	public List<Organization> loadOrganizationById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return organizationMapper.loadOrganizationByCondition(map);
	}

	public List<Organization> getOrganList(Integer parentId) {
		// TODO Auto-generated method stub
		return organizationMapper.getOrganList(parentId);
	}

	public List<Organization> getItemsByParentId(Integer id) {
		// TODO Auto-generated method stub
		return organizationMapper.getItemsByParentId(id);
	}
}
