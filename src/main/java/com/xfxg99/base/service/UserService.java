package com.xfxg99.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xfxg99.base.dao.UserMapper;

import com.xfxg99.base.model.User;

/**
 * Ïµ½y¹¦ÄÜ
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
}
