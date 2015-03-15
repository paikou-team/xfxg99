package com.xfxg99.sale.service;

import java.math.BigDecimal;
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
import com.xfxg99.sale.dao.SaleGoodsMapper;
import com.xfxg99.sale.model.SaleBill;
import com.xfxg99.sale.model.SaleGoods;
import com.xfxg99.sale.viewmodel.SaleBillVM;
import com.xfxg99.sale.viewmodel.StockGoodsVM;

@Service("saleService")
public class SaleService {
	
	@Resource(name="saleBillMapper")
	private SaleBillMapper saleBillMapper; 
	
	@Resource(name="saleGoodsMapper")
	private SaleGoodsMapper saleGoodsMapper; 
	
	@Resource(name="billSerialNoMapper")
	private BillSerialNoMapper billSerialNoMapper; 
	
	public ListResult<SaleBillVM> loadListWithPage(Map<String,Object> map){
		int total=saleBillMapper.countByVMMap(map);
		List<SaleBillVM> ls=saleBillMapper.loadListWithPage(map);
	
		return new ListResult<SaleBillVM>(total,ls);
	}
	
	public SaleBill selectByPrimaryKey(Integer id){
		return saleBillMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 保存库存单据
	 * @param bill
	 */
	@Transactional
	public void saveSaleBill(SaleBillVM bill){
		
		Map<String, Object> map=new HashMap<String,Object>();
		Map<String,Object> billNoMap=GeneralUtil.getSerialNoPars(2);
		
		double goodsAmount = 0.0f;
		if(bill.getId()==0){
			for(StockGoodsVM sg:bill.getStockGoods()){
				if(sg.getId()>0){
					goodsAmount +=  sg.getGoodsPrice()*sg.getGoodsNumber();
				}

			}

			SaleBill bs = new SaleBill();
			bs.setCustId(bill.getCustId());
			bs.setId(0);
			bs.setGoodsAmount(new BigDecimal(goodsAmount));
			bs.setOrgId(bill.getOrgId());
			bs.setPayId(0);
			bs.setSerialNo(bill.getSerialNo());
			bs.setSaleTime(bill.getSaleTime());
			bs.setRecTime(bill.getRecTime());
//			saleBillMapper.insert(bill);
			saleBillMapper.insert(bs);
			bill.setSerialNo(billSerialNoMapper.updateBillSerialNo(billNoMap));
		}else{
			saleBillMapper.updateByPrimaryKey(bill);
			map.put("saleId", bill.getId());
			List<Integer> ids=new ArrayList<Integer>();
			
			for(StockGoodsVM sg : bill.getStockGoods()){
				ids.add(sg.getId());
			}
			map.put("ids", ids);
			saleBillMapper.deleteByNotExistId(map);
		}
		
		for(StockGoodsVM sg:bill.getStockGoods()){
			sg.setStockId(bill.getId());
			if(sg.getId()>0){
				SaleGoods gs = new SaleGoods();
				gs.setId(0);
				gs.setSaleId(bill.getId());
				gs.setGoodsId(sg.getId());
				sg.getGoodsNumber();
				gs.setGoodsNumber(sg.getGoodsNumber());
				gs.setMarketPrice(sg.getMarketPrice());
				gs.setGoodsPrice(sg.getGoodsPrice());
				saleGoodsMapper.insert(gs);
			}else{
//				saleGoodsMapper.updateByPrimaryKey(sg);
			}
		}
	}
}
