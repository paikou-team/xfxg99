package com.xfxg99.sale.dao;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.PayLog;
@MyBatisRepository
public interface PayLogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(PayLog record);

    int insertSelective(PayLog record);

    PayLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(PayLog record);

    int updateByPrimaryKey(PayLog record);
}