package com.xfxg99.sale.dao;

import java.util.List;
import java.util.Map;

import com.xfxg99.core.MyBatisRepository;
import com.xfxg99.sale.model.Goods;

@MyBatisRepository
public interface GoodsMapper {
	Goods	selectByPrimaryKey(int id);
	
	List<Goods> loadListWithPage(Map<String,Object> map);
	
	int countByMap(Map<String,Object> map);
}
