package com.xfxg99.sale.controller;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xfxg99.sale.service.GoodsService;
import com.xfxg99.sale.service.StockService;

@Scope("prototype")
@Controller
@RequestMapping("/goods")
public class StockController {
	@Resource(name = "stockService")
	protected StockService stockService;
}
