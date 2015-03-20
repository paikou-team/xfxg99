package com.xfxg99.base.dao;

import java.util.List;

import com.xfxg99.base.model.SysFunction;
import com.xfxg99.core.MyBatisRepository;

@MyBatisRepository
public interface SysFunctionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysFunction record);

    int insertSelective(SysFunction record);

    SysFunction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysFunction record);

    int updateByPrimaryKey(SysFunction record);
    
    List<SysFunction> loadAllSysFunction();
    
    List<SysFunction> loadMainMenu();
}