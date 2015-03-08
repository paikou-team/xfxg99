package com.xfxg99.sale.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xfxg99.sale.dao.BillSerialNoMapper;

@Service("billSerialNoService")
public class BillSerialNoService {
	@Resource(name="billSerialNoMapper")
	private BillSerialNoMapper billSerialNoMapper; 
	
	
	public String getNextBillSerialNo(Map<String,Object> map){
		return billSerialNoMapper.getNextBillSerialNo(map);
	}
	
	public String updateBillSerialNo(Map<String,Object> map){
		return billSerialNoMapper.updateBillSerialNo(map);
	}
}
