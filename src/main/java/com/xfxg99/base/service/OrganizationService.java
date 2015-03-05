package com.xfxg99.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xfxg99.base.dao.OrganizationMapper;

import com.xfxg99.base.model.Organization;

/**
 * Ïµ½y¹¦ÄÜ
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
}
