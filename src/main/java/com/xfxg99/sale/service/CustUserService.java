package com.xfxg99.sale.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
 
import com.xfxg99.base.dao.BirthdayInfoMapper;
import com.xfxg99.base.model.BirthdayInfo;
import com.xfxg99.core.ListResult; 
import com.xfxg99.sale.dao.CustomerMapper;
import com.xfxg99.sale.viewmodel.CustomerVM;


@Service("custuserService")
public class CustUserService {

	@Resource(name="customerMapper")
	private CustomerMapper customerMapper; 
	
	@Resource(name="birthdayInfoMapper")
	private BirthdayInfoMapper birthdayInfoMapper; 
	
	public ListResult<CustomerVM> loadrechargeCustUserlist(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		
				int count=customerMapper.getCustUserCount();
				List<CustomerVM> ls=customerMapper.loadCustUserlist(map);
				
				ListResult<CustomerVM> result=new ListResult<CustomerVM>(count,ls);
				
				return result; 
	}
	public void insert(CustomerVM customer) {
		// TODO Auto-generated method stub
		customerMapper.insert(customer);
	}
	public void updateByPrimaryKey(CustomerVM customer) {
		// TODO Auto-generated method stub
		customerMapper.updateByPrimaryKey(customer);
	}  
	
	
	public CustomerVM getCustomer(Integer id){
		
		return customerMapper.loadVMById(id);
	}
	
	public ListResult<CustomerVM> loadBirthdayInfoList(Map<String ,Object> map){
		
		int count=customerMapper.countBirthdayInfo(map);
		
		List<CustomerVM> cs=customerMapper.loadBirthdayInfoList(map);
		
		ListResult<CustomerVM> result=new ListResult<CustomerVM>(count,cs);
		
		return result;
	} 
	

}
