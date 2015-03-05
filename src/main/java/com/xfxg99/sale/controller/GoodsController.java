package com.xfxg99.sale.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.sale.service.GoodsService;

@Scope("prototype")
@Controller
@RequestMapping("/goods")
public class GoodsController {
	@Resource(name = "goodsService")
	protected GoodsService goodsService;
	
	/**
	 * 
	 * @param query
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	public @ResponseBody String loadGoods(
			@RequestParam(value = "name", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request
			){
		
		return null;
	}
}
