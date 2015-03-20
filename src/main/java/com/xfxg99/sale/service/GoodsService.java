package com.xfxg99.sale.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xfxg99.core.ListResult;
import com.xfxg99.sale.dao.GoodsMapper;
import com.xfxg99.sale.model.Goods;

@Service("goodsService")
public class GoodsService {

	@Resource(name="goodsMapper")
	private GoodsMapper goodsMapper; 
	
	public ListResult<Goods>  loadWithPage(Map<String,Object> map){
		
		int count=goodsMapper.countByMap(map);
		List<Goods> ls=goodsMapper.loadListWithPage(map);
		
		ListResult<Goods> result=new ListResult<Goods>(count,ls);
		
		return result;
	}
}
