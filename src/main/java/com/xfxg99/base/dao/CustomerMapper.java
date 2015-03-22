package com.xfxg99.base.dao;

import java.util.List;
import java.util.Map;

import com.xfxg99.base.viewmodel.CustomerVM;
import com.xfxg99.core.MyBatisRepository;


@MyBatisRepository
public interface CustomerMapper {

	int countVMByMap(Map<String,Object> map);
    
    List<CustomerVM> loadVMListWithPage(Map<String,Object> map);
    
    CustomerVM loadVMById(Integer id);
    

	 int getCustUserCount();
		 

	 List<CustomerVM> loadCustUserlist(Map<String, Object> map);


	void insert(CustomerVM customer);


	void updateByPrimaryKey(CustomerVM customer);
 
	List<CustomerVM> loadBirthdayInfoList(Map<String ,Object> map);
	
	int countBirthdayInfo(Map<String ,Object> map);
 
 
}