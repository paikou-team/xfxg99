package com.xfxg99.sale.dao;

import java.util.List;
import java.util.Map;

import com.xfxg99.base.viewmodel.CustomerVM;
import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.OrderAction;
import com.xfxg99.sale.model.OrderGoods;
import com.xfxg99.sale.model.OrderInfo;
import com.xfxg99.sale.model.SaleBill;
import com.xfxg99.sale.viewmodel.SaleBillVM;
@MyBatisRepository
public interface SaleBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SaleBill record);

    int insertSelective(SaleBill record);

    SaleBill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SaleBill record);

    int updateByPrimaryKey(SaleBill record);
    
    int countByVMMap(Map<String,Object> map);
    
    List<SaleBillVM> loadListWithPage(Map<String,Object> map);
    
    void deleteByNotExistId(Map<String,Object> map);

	void insertAccountLog(Map<String, Object> map);

	int insertOrderInfo(OrderInfo order);

	void insertOrderGoods(OrderGoods og);

	void insertOrderAction(OrderAction orderaction);

	CustomerVM getCustomerInfoById(int custId);

	void updateUserInfoById(Map<String, Object> map);
	
	SaleBillVM loadVMById(Integer id);
}