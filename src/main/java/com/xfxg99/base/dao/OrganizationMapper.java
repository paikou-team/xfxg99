package com.xfxg99.base.dao;

import java.util.List;
import java.util.Map;

import com.xfxg99.base.model.Organization;
import com.xfxg99.core.MyBatisRepository;

@MyBatisRepository
public interface OrganizationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Organization record);

    int insertSelective(Organization record);

    Organization selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);
    
    List<Organization> loadAllOrganization();
    
    List<Organization> getParentIdItems(Integer id);
    
    Integer getMaxId();
    
    List<Organization> loadStockOrg();

	List<Organization> loadOrganizationByCondition(Map<String, Object> map);

	List<Organization> getOrganList(Integer parentId);
}