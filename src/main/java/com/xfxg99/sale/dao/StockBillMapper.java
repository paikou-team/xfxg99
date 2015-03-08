package com.xfxg99.sale.dao;

import java.util.List;
import java.util.Map;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.StockBill;
import com.xfxg99.sale.viewmodel.StockBillVM;

@MyBatisRepository
public interface StockBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockBill record);

    int insertSelective(StockBill record);

    StockBill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockBill record);

    int updateByPrimaryKey(StockBill record);
    
    int countVMByMap(Map<String,Object> map);
    
    List<StockBillVM> loadVMListWithPage(Map<String,Object> map);
}