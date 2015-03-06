package com.xfxg99.sale.controller;

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

import com.xfxg99.core.ListResult;
import com.xfxg99.sale.model.Goods;
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
	@RequestMapping(value = "loadGoodsList.do",produces = "application/json;charset=UTF-8")
	public @ResponseBody String loadGoodsList(
			@RequestParam(value = "goodsQuery", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request
			){
		
		JSONObject joQuery = JSONObject.fromObject(query);
		String goodsName=null;
		if(joQuery.containsKey("goodsName")){
			goodsName = joQuery.getString("goodsName").trim();
			
		}
		
		if(goodsName == null || goodsName.length()==0){
			goodsName=null;
		}
			
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		//page = page == 0 ? 1 : page;
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("goodsName",goodsName);
		
		ListResult<Goods> rs = goodsService.loadWithPage(map);
		
		return rs.toJson();
	}
}
