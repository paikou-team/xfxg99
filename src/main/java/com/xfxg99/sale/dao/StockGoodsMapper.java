package com.xfxg99.sale.dao;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.StockGoods;
@MyBatisRepository
public interface StockGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockGoods record);

    int insertSelective(StockGoods record);

    StockGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockGoods record);

    int updateByPrimaryKey(StockGoods record);
}