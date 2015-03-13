package com.xfxg99.base.dao;

import com.xfxg99.base.model.SysParameter;
import com.xfxg99.core.MyBatisRepository;
@MyBatisRepository
public interface SysParameterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysParameter record);

    int insertSelective(SysParameter record);

    SysParameter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysParameter record);

    int updateByPrimaryKey(SysParameter record);
}