package com.xfxg99.base.dao;

import java.util.List;

import com.xfxg99.base.model.Authorize;
import com.xfxg99.core.MyBatisRepository;

@MyBatisRepository
public interface AuthorizeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Authorize record);

    int insertSelective(Authorize record);

    Authorize selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Authorize record);

    int updateByPrimaryKey(Authorize record);
    
    List<Authorize> getFunctionByUserId(Integer userId);
    
    int deleteByUserId(Integer userId);
}