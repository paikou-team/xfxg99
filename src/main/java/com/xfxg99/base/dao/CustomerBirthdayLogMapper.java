package com.xfxg99.base.dao;

import com.xfxg99.base.model.CustomerBirthdayLog;
import com.xfxg99.core.MyBatisRepository;

@MyBatisRepository
public interface CustomerBirthdayLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerBirthdayLog record);

    int insertSelective(CustomerBirthdayLog record);

    CustomerBirthdayLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerBirthdayLog record);

    int updateByPrimaryKey(CustomerBirthdayLog record);
}