package com.xfxg99.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xfxg99.base.dao.AuthorizeMapper;

import com.xfxg99.base.model.Authorize;

/**
 * 
 * @author sam
 *
 */
@Service("authorizeService")
public class AuthorizeService{
	
	@Resource(name="authorizeMapper")
	private AuthorizeMapper authorizeMapper;
	
	 public  List<Authorize>  getFunctionByUserId(Integer userId){
		 
		 return authorizeMapper.getFunctionByUserId(userId);
		 
	 }
	 
	 public int insert(Authorize authorize)
	 {
		 return authorizeMapper.insert(authorize);
	 }
	 
	 public void insert(Integer userId,Integer funId)
	 {
		 Authorize insettmp = new Authorize();
		 insettmp.setUserId(userId);
		 insettmp.setFunctionId(funId);
		 
		 authorizeMapper.insert(insettmp);
		 
	 }
	 
	 public int deleteByUserId(Integer userId)
	 {
		 return authorizeMapper.deleteByUserId(userId);
	 }
}
