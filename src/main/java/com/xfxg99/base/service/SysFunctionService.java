package com.xfxg99.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xfxg99.base.dao.SysFunctionMapper;

import com.xfxg99.base.model.SysFunction;

/**
 * Ïµ½y¹¦ÄÜ
 * @author Owen
 *
 */
@Service("sysFunctionService")
public class SysFunctionService{
	
	@Resource(name="sysFunctionMapper")
	private SysFunctionMapper sysFunctionMapper;
	
	 public  List<SysFunction>  loadAllSysFunction(){
		 
		 return sysFunctionMapper.loadAllSysFunction();
		 
	 }
	 
	 public List<SysFunction> loadMainMenu(){
		 return sysFunctionMapper.loadMainMenu();
	 }
}
