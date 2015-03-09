package com.xfxg99.sale.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
   
import com.xfxg99.core.ListResult;
import com.xfxg99.sale.dao.RechargeMapper; 
import com.xfxg99.sale.model.Recharge;
import com.xfxg99.sale.viewmodel.CustomerVM;
import com.xfxg99.sale.viewmodel.RechargeVM;
@Service("rechargeService")
public class RechargeService {
	@Resource(name="rechargeMapper")
	private RechargeMapper rechargeMapper; 
	
	public ListResult<RechargeVM>  loadrechargelist(Map<String,Object> map){
		
		int count=rechargeMapper.countByMap(map);
		List<RechargeVM> ls=rechargeMapper.loadrechargelistWithPage(map);
		
		ListResult<RechargeVM> result=new ListResult<RechargeVM>(count,ls);
		
		return result;
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
}
