package com.xfxg99.sale.dao;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.SaleGoods;
@MyBatisRepository
public interface SaleGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SaleGoods record);

    int insertSelective(SaleGoods record);

    SaleGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SaleGoods record);

    int updateByPrimaryKey(SaleGoods record);
}