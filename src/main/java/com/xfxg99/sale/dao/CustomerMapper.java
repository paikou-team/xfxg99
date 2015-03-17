package com.xfxg99.sale.dao;

import java.util.List;
import java.util.Map;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.viewmodel.CustomerVM;


@MyBatisRepository
public interface CustomerMapper {

	int countVMByMap(Map<String,Object> map);
    
    List<CustomerVM> loadVMListWithPage(Map<String,Object> map);
    
    CustomerVM loadVMById(Integer id);
    

	 int getCustUserCount();
		 

	 List<CustomerVM> loadCustUserlist(Map<String, Object> map);


	void insert(CustomerVM customer);


	void updateByPrimaryKey(CustomerVM customer);
 
 
}