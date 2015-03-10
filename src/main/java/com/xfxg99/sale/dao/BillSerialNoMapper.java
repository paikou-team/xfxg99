package com.xfxg99.sale.dao;

import java.util.Map;

import com.xfxg99.core.MyBatisRepository;

@MyBatisRepository
public interface BillSerialNoMapper {

	String getNextBillSerialNo(Map<String,Object> map);
	String updateBillSerialNo(Map<String,Object> map);
}
