package com.xfxg99.sale.dao;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.StockBill;
@MyBatisRepository
public interface StockBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockBill record);

    int insertSelective(StockBill record);

    StockBill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockBill record);

    int updateByPrimaryKey(StockBill record);
}