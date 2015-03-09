package com.xfxg99.sale.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.User;
import com.xfxg99.core.GeneralUtil;
import com.xfxg99.core.Result;
import com.xfxg99.sale.service.BillSerialNoService;
import com.xfxg99.sale.service.StockService;
import com.xfxg99.sale.viewmodel.StockBillVM;
import com.xfxg99.sale.viewmodel.StockGoodsVM;

@Scope("prototype")
@Controller
@RequestMapping("/stock")
public class StockController {
	
	@Resource(name = "stockService")
	protected StockService stockService;
	
	@Resource(name = "billSerialNoService")
	protected BillSerialNoService billSerialNoService;
	/**
	 * 获取一张新的单据
	 * 不包含分录
	 * @param billType
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getNewBill.do",produces = "application/json;charset=UTF-8")
	public  @ResponseBody String getNewBill(
			@RequestParam(value = "billType", required = false) Integer billType,
			HttpServletRequest request
			){
		//测试
		User u=new User();
		u.setId(9);
		u.setName("李如江");
		u.setOrgId(8);
		u.setPassword("123");
		
		StockBillVM  bill=new StockBillVM();
		
		Date ct=Calendar.getInstance().getTime();
		
		bill.setBillType(billType);
		bill.setBillTime(ct);
		
		Map<String,Object> billNoMap=GeneralUtil.getSerialNoPars(billType);
		String billNo=billSerialNoService.getNextBillSerialNo(billNoMap);
		
		bill.setSerialNo(billNo);
		bill.setId(0);
		bill.setPreparerId(u.getId());
		bill.setPrepareTime(ct);
		bill.setPreparerName(u.getName());
		bill.setState(0);
		
		return null;
	}
	
	/**
	 * 读取一张单据
	 * 如果id=0，表示新建一个单据返
	 * @return
	 */
	@RequestMapping(value = "loadBill.do",produces = "application/json;charset=UTF-8")
	public  @ResponseBody String loadBill(
			@RequestParam(value = "billType", required = false) Integer billType,
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request
			){

		//测试
		User u=new User();
		u.setId(9);
		u.setName("李如江");
		u.setOrgId(8);
		u.setPassword("123");
		
		
		StockBillVM bill=null;
		
		if(id==0){//新建一个单据
			bill=this.newStockBill(billType, u);
		}else{//从数据库读取一个单据
			
		}
		
		Result<StockBillVM> result=new Result<StockBillVM>(bill);

		return result.toJson();
	}
	
	
	@RequestMapping(value = "saveStockBill.do",produces = "application/json;charset=UTF-8")
	public  @ResponseBody String loadBill(
			@RequestParam(value = "bill", required = false) String billJson,
			HttpServletRequest request
			){
		Result<StockBillVM>  result =null;
		
		try{
			JSONObject jObj = JSONObject.fromObject(billJson);
			
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

			classMap.put("stockGoods", StockGoodsVM.class);
			
			StockBillVM bill = (StockBillVM) JSONObject.toBean(jObj, StockBillVM.class, classMap);
			
			stockService.saveStockBill(bill);

			result=new Result<StockBillVM>(bill);
			
		}catch(Exception ex){
			result=new Result<StockBillVM>(null,false,true,true,ex.getMessage());
		}
		return result.toJson();

	}

	
	/**
	 * 创建一个单据
	 * @param billType
	 * @param u
	 * @return
	 */
	private StockBillVM newStockBill(Integer billType,User u){
		StockBillVM  bill=new StockBillVM();
		
		Date ct=Calendar.getInstance().getTime();
		
		bill.setBillType(billType);
		bill.setBillTime(ct);
		
		Map<String,Object> billNoMap=GeneralUtil.getSerialNoPars(billType);
		String billNo=billSerialNoService.getNextBillSerialNo(billNoMap);
		
		bill.setSerialNo(billNo);
		bill.setId(0);
		bill.setPreparerId(u.getId());
		bill.setPrepareTime(ct);
		bill.setPreparerName(u.getName());
		bill.setState(0);
		
		return bill;
	}
	
	
	 
}
