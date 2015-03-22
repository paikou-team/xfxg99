package com.xfxg99.sale.dao;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.OrderAction;
@MyBatisRepository
public interface OrderActionMapper {
    int deleteByPrimaryKey(Integer actionId);

    int insert(OrderAction record);

    int insertSelective(OrderAction record);

    OrderAction selectByPrimaryKey(Integer actionId);

    int updateByPrimaryKeySelective(OrderAction record);

    int updateByPrimaryKey(OrderAction record);
}