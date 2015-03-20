package com.xfxg99.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xfxg99.base.dao.BirthdayInfoMapper;
import com.xfxg99.base.model.BirthdayInfo;
import com.xfxg99.sale.viewmodel.CustomerVM;

@Service("birthdayInfoService")
public class BirthdayInfoService {

	@Resource(name="birthdayInfoMapper")
	private BirthdayInfoMapper birthdayInfoMapper;
	
	public BirthdayInfo loadBirthdayInfo(Integer custId,Integer birthdayYear){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("custId", custId);
		map.put("birthdayYear", birthdayYear);
		
		return birthdayInfoMapper.loadBirthdayInfo(map);
	}
	

	public void saveBirthdayInfo(BirthdayInfo data){
		if(data.getId()>0){
			birthdayInfoMapper.updateByPrimaryKey(data);
		}else{
			birthdayInfoMapper.insert(data);
		}
	}
	
}
