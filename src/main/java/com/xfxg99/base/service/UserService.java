package com.xfxg99.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xfxg99.base.dao.UserMapper;

import com.xfxg99.base.model.User;

/**
 * ϵ�y����
 * @author Sam
 *
 */
@Service("userService")
public class UserService{
	
	@Resource(name="userMapper")
	private UserMapper userMapper;
	
	 public  List<User>  loadAllUser(){
		 
		 return userMapper.loadAllUser();
		 
	 }
	 
	 public int deleteUser(Integer id){
		 
		 return userMapper.deleteByPrimaryKey(id);
	 }
	 
	 public int saveUser(User user){
		 if(user != null)
		 {
			 if(user.getId()>0)
			 {
				 return userMapper.updateByPrimaryKeySelective(user);
			 }
			 else
			 {
				 Integer result =  userMapper.insert(user);
				 if(result != 0 )
				 {
					 user.setId(getMaxId());
					 return result;
				 }
			 }
		 }
		 return 0;
	 }
	 
	 public Integer getMaxId()
	 {
		 return userMapper.getMaxId();
	 }
}
