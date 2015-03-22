package com.xfxg99.sale.dao;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.OrderGoods;
@MyBatisRepository
public interface OrderGoodsMapper {
    int deleteByPrimaryKey(Integer recId);

    int insert(OrderGoods record);

    int insertSelective(OrderGoods record);

    OrderGoods selectByPrimaryKey(Integer recId);

    int updateByPrimaryKeySelective(OrderGoods record);

    int updateByPrimaryKeyWithBLOBs(OrderGoods record);

    int updateByPrimaryKey(OrderGoods record);
}