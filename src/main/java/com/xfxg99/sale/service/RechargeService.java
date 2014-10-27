package com.xfxg99.sale.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
   
import com.xfxg99.base.viewmodel.CustomerVM;
import com.xfxg99.core.ListResult;
import com.xfxg99.sale.dao.RechargeMapper; 
import com.xfxg99.sale.model.Recharge;
import com.xfxg99.sale.viewmodel.RechargeVM;
@Service("rechargeService")
public class RechargeService {
	@Resource(name="rechargeMapper")
	private RechargeMapper rechargeMapper; 
	
	public ListResult<RechargeVM>  loadrechargelist(Map<String,Object> map){
		
		int count=rechargeMapper.countByMap(map);
		List<RechargeVM> ls=rechargeMapper.loadrechargelistWithPage(map);
		//int count = ls.size();
		ListResult<RechargeVM> result=new ListResult<RechargeVM>(count,ls);
		
		return result;
	}
	public List<RechargeVM> loadTotalrechargelist(Map<String, Object> map) {
		List<RechargeVM> ls=rechargeMapper.loadrechargelistWithPage(map);
		return ls;
	}

	public List<CustomerVM> selectCustomerList() {
		// TODO Auto-generated method stub
		return rechargeMapper.selectCustomerList();
	}

	public List<CustomerVM> selectOrganList() {
		// TODO Auto-generated method stub
		return rechargeMapper.selectOrganList();
	}

	public int updateByPrimaryKey(Recharge charge) {
		// TODO Auto-generated method stub
		return rechargeMapper.updateByPrimaryKey(charge);
	}

	public int insert(Recharge charge) {
		// TODO Auto-generated method stub
		return rechargeMapper.insert(charge);
	}

	public Recharge selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return rechargeMapper.selectByPrimaryKey(id);
	}

	public void insertAccountLog(Map<String, Object> map) {
		// TODO Auto-generated method stub
		rechargeMapper.insertAccountLog(map);
	}

	public ListResult<CustomerVM> loadrechargeCustUserlist(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		int count=rechargeMapper.getCustUserCount(map); 
		List<CustomerVM> ls=rechargeMapper.loadrechargeCustUserlist(map);
		
		ListResult<CustomerVM> result=new ListResult<CustomerVM>(count,ls);
		
		return result; 
	} 
	public void updateOrgIdByPrimaryKey(Map<String, Object> map) {
		// TODO Auto-generated method stub
		rechargeMapper.updateOrgIdByPrimaryKey(map);
	}  
	
	@Transactional
	public void saveRecharge(Map<String, Object> map){
		rechargeMapper.insertAccountLog(map);
		rechargeMapper.updateCustomerMoney(map);
	}

	
}
