package com.xfxg99.sale.dao;

import java.util.List;
import java.util.Map;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.StockBill;
import com.xfxg99.sale.viewmodel.InventoryVM;
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
    
    void deleteByNotExistId(Map<String,Object> map);
    
    void confirmStockBill(Map<String,Object> map);
    
    
    StockBillVM loadVMById(Integer id);
    
    int getBillConfirmerId(Integer id);
    
    List<InventoryVM> loadInventoryList(Map<String,Object> map);

	int loadInventoryCount(Map<String, Object> map);
}