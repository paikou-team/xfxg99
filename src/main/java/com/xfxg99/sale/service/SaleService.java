package com.xfxg99.sale.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xfxg99.core.GeneralUtil;
import com.xfxg99.core.ListResult;
import com.xfxg99.sale.dao.BillSerialNoMapper;
import com.xfxg99.sale.dao.SaleBillMapper;
import com.xfxg99.sale.dao.StockGoodsMapper;
import com.xfxg99.sale.model.SaleBill;

@Service("saleService")
public class SaleService {
	
	@Resource(name="saleBillMapper")
	private SaleBillMapper saleBillMapper; 
	
	@Resource(name="stockGoodsMapper")
	private StockGoodsMapper stockGoodsMapper; 
	
	@Resource(name="billSerialNoMapper")
	private BillSerialNoMapper billSerialNoMapper; 
	
	public ListResult<SaleBill> loadListWithPage(Map<String,Object> map){
		int total=saleBillMapper.countByMap(map);
		List<SaleBill> ls=saleBillMapper.loadListWithPage(map);
	
		return new ListResult<SaleBill>(total,ls);
	}
	
	public SaleBill selectByPrimaryKey(Integer id){
		return saleBillMapper.selectByPrimaryKey(id);
	}
	
	
}
