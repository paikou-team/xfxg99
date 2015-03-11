package com.xfxg99.sale.dao;

import java.util.List;
import java.util.Map;
 
import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.Recharge;
import com.xfxg99.sale.viewmodel.CustomerVM;
import com.xfxg99.sale.viewmodel.RechargeVM;

@MyBatisRepository
public interface RechargeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recharge record);

    int insertSelective(Recharge record);

    Recharge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Recharge record);

    int updateByPrimaryKey(Recharge record);

	int countByMap(Map<String, Object> map);

	List<RechargeVM> loadrechargelistWithPage(Map<String, Object> map);

	List<CustomerVM> selectCustomerList();

	List<CustomerVM> selectOrganList();

	void insertAccountLog(Map<String, Object> map);
     
	
}