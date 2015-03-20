package com.xfxg99.base.dao;

import java.util.List;
import java.util.Map;

import com.xfxg99.base.model.BirthdayInfo;
import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.viewmodel.CustomerVM;

@MyBatisRepository
public interface BirthdayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BirthdayInfo record);

    int insertSelective(BirthdayInfo record);

    BirthdayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BirthdayInfo record);

    int updateByPrimaryKey(BirthdayInfo record);
    
    BirthdayInfo loadBirthdayInfo(Map<String,Object> map);
    
    
}