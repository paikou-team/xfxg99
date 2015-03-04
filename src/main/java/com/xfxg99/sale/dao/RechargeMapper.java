package com.xfxg99.sale.dao;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.Recharge;

@MyBatisRepository
public interface RechargeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recharge record);

    int insertSelective(Recharge record);

    Recharge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Recharge record);

    int updateByPrimaryKey(Recharge record);
}