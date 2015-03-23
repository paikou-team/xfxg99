package com.xfxg99.sale.dao;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.OrderInfo;
@MyBatisRepository
public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}