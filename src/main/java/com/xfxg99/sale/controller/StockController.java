package com.xfxg99.sale.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.Organization;
import com.xfxg99.base.model.User;
import com.xfxg99.base.service.OrganizationService;
import com.xfxg99.base.viewmodel.UserVM;
import com.xfxg99.core.GeneralUtil;
import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result;
import com.xfxg99.sale.model.SaleBill;
import com.xfxg99.sale.service.BillSerialNoService;
import com.xfxg99.sale.service.StockService;
import com.xfxg99.sale.viewmodel.InventoryVM;
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

	@Resource(name = "organizationService")
	protected OrganizationService orgService;

	/**
	 * 获取数据列表
	 * 
	 * @param query
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "loadStockList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadStockList(
			@RequestParam(value = "stockQuery", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) {
		ListResult<StockBillVM> result = new ListResult<StockBillVM>();

		UserVM user = (UserVM) request.getSession().getAttribute("user");

		if (user == null) {
			result.setIsSuccess(false);
			result.setMsg("请重新登录");
			return result.toJson();
		}

		JSONObject joQuery = JSONObject.fromObject(query);
		Map<String, Object> map = new HashMap<String, Object>();

		int billType = joQuery.getInt("billType");
		int stockInOrgId = joQuery.getInt("stockInOrgId");
		int stockOutOrgId = joQuery.getInt("stockOutOrgId");
		String serialNo = joQuery.getString("serialNo").trim();
		serialNo = "".equals(serialNo) ? null : serialNo;
		String beginTime = joQuery.getString("beginTime");
		String endTime = joQuery.getString("endTime");
		int confirmState = joQuery.getInt("confirmState");

		page = page == 0 ? 1 : page;

		map.put("billType", billType);
		map.put("stockInOrgId", stockInOrgId);
		map.put("stockOutOrgId", stockOutOrgId);
		map.put("serialNo", serialNo);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("confirmState", confirmState);
		if (!user.getIsAllDataPermission()) {
			Organization org = orgService.getOrganization(user.getOrgId());
			map.put("userOrgPath", org.getPath());
		}
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);

		result = stockService.loadVMListWithPage(map);

		return result.toJson();
	}

	/**
	 * 读取一张单据 如果id=0，表示新建一个单据返
	 * 
	 * @return
	 */
	@RequestMapping(value = "loadBill.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadBill(
			@RequestParam(value = "billType", required = false) Integer billType,
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request) {

		Result<StockBillVM> result = new Result<StockBillVM>();
		UserVM user = (UserVM) request.getSession().getAttribute("user");

		if (user == null) {
			result = new Result<StockBillVM>(null, false, true, false, "请从新登录!");
			return result.toJson();
		}

		StockBillVM bill = null;

		if (id == 0) {// 新建一个单据
			bill = this.newStockBill(billType, user);
		} else {// 从数据库读取一个单据
			bill = stockService.loadVMById(id);
		}

		result.setData(bill);

		return result.toJson();
	}

	@RequestMapping(value = "confirmBill.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String confirmBill(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request) {

		UserVM user = (UserVM) request.getSession().getAttribute("user");
		Result<StockBillVM> result = null;

		if (user == null) {
			result = new Result<StockBillVM>(null, false, true, false, "请从新登录");
			return result.toJson();
		}

		try {

			StockBillVM bill = stockService.loadVMById(id);

			String msg = "";
			boolean isSuccess = true;

			switch (bill.getBillType()) {
			case 10:
				if (bill.getStockInOrgId() != user.getOrgId()) {
					msg = "确认人必须属于入库部门!";
					isSuccess = false;
				}
				break;
			case 11:
				if (bill.getStockOutOrgId() != user.getOrgId()) {
					msg = "确认人必须属于出库部门!";
					isSuccess = false;
				}
				break;
			case 12:
				if (bill.getStockInOrgId() != user.getOrgId()) {
					msg = "确认人必须属于入库部门!";
					isSuccess = false;
				}
				break;
			}

			if (isSuccess) {
				stockService.confirmStockBill(id, user.getId());

				bill.setConfirmerId(user.getId());
				bill.setConfirmerName(user.getName());
				bill.setConfirmerOrgId(user.getOrgId());
				bill.setConfirmerOrgName(user.getOrgName());
				bill.setConfirmTime(Calendar.getInstance().getTime());

				result = new Result<StockBillVM>(bill, true, false, false, null);
			} else {
				result = new Result<StockBillVM>(bill, false, false, false, msg);
			}

		} catch (Exception ex) {
			result = new Result<StockBillVM>(null, false, false, true,
					ex.getMessage());
		}

		return result.toJson();
	}

	@RequestMapping(value = "saveStockBill.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String saveStockBill(
			@RequestParam(value = "bill", required = false) String billJson,
			HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");

		Result<StockBillVM> result = null;

		if (user == null) {
			result = new Result<StockBillVM>(null, false, true, false, "请从新登录");
			return result.toJson();
		}

		try {
			JSONObject jObj = JSONObject.fromObject(billJson);

			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

			classMap.put("stockGoods", StockGoodsVM.class);

			StockBillVM bill = (StockBillVM) JSONObject.toBean(jObj,
					StockBillVM.class, classMap);

			stockService.saveStockBill(bill);

			result = new Result<StockBillVM>(bill);

		} catch (Exception ex) {
			result = new Result<StockBillVM>(null, false, true, true,
					ex.getMessage());
		}
		return result.toJson();

	}

	@RequestMapping(value = "delStockBill.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String delStockBill(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		Result<StockBillVM> result = null;

		if (user == null) {
			result = new Result<StockBillVM>(null, false, true, false, "请从新登录");
			return result.toJson();
		}

		int confirmerId = stockService.getBillConfirmerId(id);

		if (confirmerId > 0) {
			result = new Result<StockBillVM>(null, false, false, false,
					"单据已经审核，不能删除!");
		} else {
			stockService.deleteBillById(id);
			result = new Result<StockBillVM>(null, true, false, false, null);
		}

		return result.toJson();
	}

	@RequestMapping(value = "loadInventoryList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadInventoryList(
			@RequestParam(value = "inventoryQuery", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		ListResult<InventoryVM> result = null;

		if (user == null) {
			result = new ListResult<InventoryVM>(0, null, false);
			result.setIsSessionExpired(true);
			result.setMsg("请重新登录");
			return result.toJson();
		}

		JSONObject joQuery = JSONObject.fromObject(query);

		int orgId = joQuery.getInt("orgId");
		String goodsName = joQuery.getString("goodsName");
		goodsName = "".equals(goodsName) ? null : goodsName;

		Map<String, Object> map = new HashMap<String, Object>();
		 
		map.put("goodsName", goodsName);
		page = page == 0 ? 1 : page;

		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		if (!user.getIsAllDataPermission()) {
			Organization org = orgService.getOrganization(user.getOrgId());
			map.put("userOrgPath", org.getPath());
			map.put("orgId", orgId);
		}

		// Object x=map.get("goodsName");

		int total = stockService.loadInventoryCount(map);
		
		List<InventoryVM> ls = stockService.loadInventoryList(map);

		result = new ListResult<InventoryVM>(total, ls, true);

		return result.toJson();
	}

	/**
	 * 创建一个单据
	 * 
	 * @param billType
	 * @param u
	 * @return
	 */
	private StockBillVM newStockBill(Integer billType, UserVM u) {
		StockBillVM bill = new StockBillVM();

		Date ct = Calendar.getInstance().getTime();

		bill.setBillType(billType);
		bill.setBillTime(ct);

		Map<String, Object> billNoMap = GeneralUtil.getSerialNoPars(billType);
		String billNo = billSerialNoService.getNextBillSerialNo(billNoMap);

		bill.setSerialNo(billNo);
		bill.setId(0);
		bill.setPreparerOrgId(u.getOrgId());
		bill.setPreparerOrgName(u.getOrgName());
		bill.setPreparerId(u.getId());
		bill.setPrepareTime(ct);
		bill.setPreparerName(u.getName());
		bill.setState(0);

		return bill;
	}

}
