package com.xfxg99.sale.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.core.GeneralUtil;
import com.xfxg99.sale.service.BillSerialNoService;

@Scope("prototype")
@Controller
@RequestMapping("/general")
public class GeneralController {
	
	@Resource(name = "billSerialNoService")
	protected BillSerialNoService billSerialNoService;
	
	@RequestMapping(value = "getNextBillSerialNo.do",produces = "application/json;charset=UTF-8")
	public @ResponseBody String getNextBillSerialNo(
			@RequestParam(value = "billType", required = false) Integer billType,
			HttpServletRequest request
			){
		
		Map<String,Object> map=GeneralUtil.getSerialNoPars(billType);
		
		return billSerialNoService.getNextBillSerialNo(map);
	}
	
}
