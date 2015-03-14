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

import com.xfxg99.base.viewmodel.UserVM;
import com.xfxg99.sale.viewmodel.SaleBillVM;
import com.xfxg99.sale.viewmodel.StockBillVM;
import com.xfxg99.core.GeneralUtil;
import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result;
import com.xfxg99.sale.model.SaleBill;
import com.xfxg99.sale.service.BillSerialNoService;
import com.xfxg99.sale.service.SaleService;

@Scope("prototype")
@Controller
@RequestMapping("/sale")
public class SaleController {
	
	@Resource(name = "saleService")
	protected SaleService saleService;
	@Resource(name = "billSerialNoService")
	protected BillSerialNoService billSerialNoService;
	
	
	
	/**
	 * 获取数据列表
	 * @param query
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "loadSaleList.do",produces = "application/json;charset=UTF-8")
	public  @ResponseBody String loadSaleList(
			@RequestParam(value = "saleQuery", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request
			){
		
		Result<SaleBill> result=new Result<SaleBill>(); 
		UserVM user =(UserVM)request.getSession().getAttribute("user");
		
		if(user ==null){
			result =new Result<SaleBill>(null,false,true,false,"请从新登录!");
			return result.toJson();
		}
		
		JSONObject joQuery = JSONObject.fromObject(query);
		Map<String,Object> map=new HashMap<String,Object>();
		
		int orgId=joQuery.getInt("orgId");
		
		if(user.getIsAllDataPermission() == false)
		{
			if(user.getOrgId() != orgId)
			{
				result =new Result<SaleBill>(null,false,true,false,"没有权限读取部门数据!");
				return result.toJson();
			}
		}
		
		String beginTime=joQuery.getString("beginTime");
		String endTime=joQuery.getString("endTime");
		
		page = page == 0 ? 1 : page;
		
		map.put("orgId", orgId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		
		ListResult<SaleBillVM> ls=saleService.loadListWithPage(map);
		
		return ls.toJson();
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

		Result<SaleBill> result=new Result<SaleBill>(); 
		UserVM user =(UserVM)request.getSession().getAttribute("user");
		
		if(user ==null){
			result =new Result<SaleBill>(null,false,true,false,"请从新登录!");
			return result.toJson();
		}
		
		SaleBillVM bill=null;
		
		if(id==0){//新建一个单据
			bill=this.newSaleBill(billType, user);
		}else{//从数据库读取一个单据
//			bill = saleService.loadVMById(id);
		}
		
		result.setData(bill);

		return result.toJson();
	}
	
	/**
	 * 创建一个单据
	 * @param billType
	 * @param u
	 * @return
	 */
	private SaleBillVM newSaleBill(Integer billType,UserVM u){
		SaleBillVM  bill=new SaleBillVM();
		
		Date ct=Calendar.getInstance().getTime();
		
	
		bill.setSaleTime(ct);
		
		Map<String,Object> billNoMap=GeneralUtil.getSerialNoPars(billType);
		String billNo=billSerialNoService.getNextBillSerialNo(billNoMap);
		
		bill.setSerialNo(billNo);
		bill.setPayId(0);
		bill.setId(0);
		bill.setOrgId(u.getOrgId());
		bill.setOrgName(u.getOrgName());
		
		bill.setRecTime(ct);
		bill.setPreparerOrgName(u.getOrgName());
		bill.setPreparerName(u.getName());
//		bill.setState(0);
		
		return bill;
	}
	
	 
}
