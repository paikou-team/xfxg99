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
		 
		 return userMapper.insert(user);
	 }
}
