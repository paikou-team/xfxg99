package com.xfxg99.sale.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.User;
import com.xfxg99.core.GeneralUtil;
import com.xfxg99.sale.service.BillSerialNoService;
import com.xfxg99.sale.service.GoodsService;
import com.xfxg99.sale.service.StockService;
import com.xfxg99.sale.viewmodel.StockBillVM;

@Scope("prototype")
@Controller
@RequestMapping("/goods")
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
	public  @ResponseBody String loadGoodsList(
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
}
