package com.xfxg99.sale.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xfxg99.core.ListResult;
import com.xfxg99.sale.viewmodel.StockBillVM;
import com.xfxg99.sale.viewmodel.StockGoodsVM;
import com.xfxg99.sale.dao.StockBillMapper;
import com.xfxg99.sale.dao.StockGoodsMapper;

@Service("stockService")
public class StockService {
	
	@Resource(name="stockBillMapper")
	private StockBillMapper stockBillMapper; 
	
	@Resource(name="stockGoodsMapper")
	private StockGoodsMapper stockGoodsMapper; 
	
	public ListResult<StockBillVM> loadVMListWithPage(Map<String,Object> map){
		int total=stockBillMapper.countVMByMap(map);
		List<StockBillVM> ls=stockBillMapper.loadVMListWithPage(map);
	
		return new ListResult<StockBillVM>(total,ls);

	}
	/**
	 * 保存库存单据
	 * @param bill
	 */
	@Transactional
	public void saveStockBill(StockBillVM bill){
		
		Map<String, Object> map=new HashMap<String,Object>();
		
		if(bill.getId()==0){
			stockBillMapper.insert(bill);
		}else{
			stockBillMapper.updateByPrimaryKey(bill);
			map.put("stockId", bill.getId());
			List<Integer> ids=new ArrayList<Integer>();
			
			for(StockGoodsVM sg : bill.getStockGoods()){
				ids.add(sg.getId());
			}
			map.put("ids", ids);
			stockBillMapper.deleteByNotExistId(map);
		}
		
		for(StockGoodsVM sg:bill.getStockGoods()){
			sg.setStockId(bill.getId());
			if(sg.getId()==0){
				stockGoodsMapper.insert(sg);
			}else{
				stockGoodsMapper.updateByPrimaryKey(sg);
			}
		}
	}
}
