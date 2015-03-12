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
import com.xfxg99.sale.viewmodel.StockBillVM;
import com.xfxg99.sale.viewmodel.StockGoodsVM;
import com.xfxg99.sale.dao.BillSerialNoMapper;
import com.xfxg99.sale.dao.StockBillMapper;
import com.xfxg99.sale.dao.StockGoodsMapper;

@Service("stockService")
public class StockService {
	
	@Resource(name="stockBillMapper")
	private StockBillMapper stockBillMapper; 
	
	@Resource(name="stockGoodsMapper")
	private StockGoodsMapper stockGoodsMapper; 
	
	@Resource(name="billSerialNoMapper")
	private BillSerialNoMapper billSerialNoMapper; 
	
	public ListResult<StockBillVM> loadVMListWithPage(Map<String,Object> map){
		int total=stockBillMapper.countVMByMap(map);
		List<StockBillVM> ls=stockBillMapper.loadVMListWithPage(map);
	
		return new ListResult<StockBillVM>(total,ls);
	}
	
	public StockBillVM loadVMById(Integer id){
		return stockBillMapper.loadVMById(id);
	}
	
	/**
	 * 保存库存单据
	 * @param bill
	 */
	@Transactional
	public void saveStockBill(StockBillVM bill){
		
		Map<String, Object> map=new HashMap<String,Object>();
		Map<String,Object> billNoMap=GeneralUtil.getSerialNoPars(bill.getBillType());
		
		if(bill.getId()==0){
			stockBillMapper.insert(bill);
			bill.setSerialNo(billSerialNoMapper.updateBillSerialNo(billNoMap));
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
	
	@Transactional
	public void confirmStockBill(Integer billId,Integer userId){
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("billId", billId);
		map.put("userId", userId);
		
		stockBillMapper.confirmStockBill(map);
	}
	
	
	public int getBillConfirmerId(Integer id){
		return stockBillMapper.getBillConfirmerId(id);
	}
	
	public void deleteBillById(Integer id){
		stockBillMapper.deleteByPrimaryKey(id);
	}
	
}
