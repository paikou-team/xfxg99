package com.xfxg99.base.dao;

import java.util.List;
import com.xfxg99.base.model.User;
import com.xfxg99.core.MyBatisRepository;

@MyBatisRepository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> loadAllUser();
}