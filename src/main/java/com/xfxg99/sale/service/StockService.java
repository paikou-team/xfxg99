package com.xfxg99.sale.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xfxg99.core.ListResult;
import com.xfxg99.sale.viewmodel.StockBillVM;
import com.xfxg99.sale.dao.StockBillMapper;

@Service("stockService")
public class StockService {
	
	@Resource(name="stockBillMapper")
	private StockBillMapper stockBillMapper; 
	
	public ListResult<StockBillVM> loadVMListWithPage(Map<String,Object> map){
		int total=stockBillMapper.countVMByMap(map);
		List<StockBillVM> ls=stockBillMapper.loadVMListWithPage(map);
	
		return new ListResult<StockBillVM>(total,ls);

	}
}
