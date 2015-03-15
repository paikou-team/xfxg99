package com.xfxg99.base.dao;

import java.util.List;
import java.util.Map;

import com.xfxg99.base.model.User;
import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.base.viewmodel.UserVM;;

@MyBatisRepository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> loadAllUser();
    
    Integer getMaxId();

	UserVM loadUserByNameAndPwd(Map<String, Object> map);
	
	List<UserVM> loadUserlistWithPage(Map<String, Object> map);
	
	int countByMap(Map<String, Object> map);


	int checkAuthorize(Map<String, Object> map);

	
	int getUsedOrgIdCount(Integer orgId);


}
