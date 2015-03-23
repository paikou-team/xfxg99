package com.xfxg99.sale.controller;

import java.math.BigDecimal;
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
import com.xfxg99.base.viewmodel.CustomerVM;
import com.xfxg99.base.viewmodel.UserVM;
import com.xfxg99.sale.viewmodel.SaleBillVM;
import com.xfxg99.sale.viewmodel.StockGoodsVM;
import com.xfxg99.core.GeneralUtil;
import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result;
import com.xfxg99.core.Sms;
import com.xfxg99.core.SmsInfo;
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
	 * 
	 * @param query
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "loadSaleList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadSaleList(
			@RequestParam(value = "saleQuery", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) {

		Result<SaleBill> result = new Result<SaleBill>();
		UserVM user = (UserVM) request.getSession().getAttribute("user");

		if (user == null) {
			result = new Result<SaleBill>(null, false, true, false, "请从新登录!");
			return result.toJson();
		}

		JSONObject joQuery = JSONObject.fromObject(query);
		Map<String, Object> map = new HashMap<String, Object>();

		int orgId = joQuery.getInt("orgId");

		if (user.getIsAllDataPermission() == false) {
			if (user.getOrgId() != orgId) {
				result = new Result<SaleBill>(null, false, true, false,
						"没有权限读取部门数据!");
				return result.toJson();
			}
		}

		String beginTime = joQuery.getString("beginTime");
		String endTime = joQuery.getString("endTime");
		String serialNo = joQuery.getString("serialNo");

		page = page == 0 ? 1 : page;

		map.put("orgId", orgId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("serialNo", serialNo);

		ListResult<SaleBillVM> ls = saleService.loadListWithPage(map);

		return ls.toJson();
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

		Result<SaleBill> result = new Result<SaleBill>();
		UserVM user = (UserVM) request.getSession().getAttribute("user");

		if (user == null) {
			result = new Result<SaleBill>(null, false, true, false, "请从新登录!");
			return result.toJson();
		}

		SaleBillVM bill = null;

		if (id == 0) {// 新建一个单据
			bill = this.newSaleBill(billType, user);
		} else {// 从数据库读取一个单据
			// bill = saleService.loadVMById(id);
		}

		result.setData(bill);

		return result.toJson();
	}

	/**
	 * 读取一张单据 如果id=0，表示新建一个单据返
	 * 
	 * @return
	 */
	@RequestMapping(value = "loadProductListByBillId.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadProductListByBillId(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request) {
		Result<SaleBill> result = new Result<SaleBill>();
		Map<String, Object> map = new HashMap<String, Object>();
		UserVM user = (UserVM) request.getSession().getAttribute("user");

		if (user == null) {
			result = new Result<SaleBill>(null, false, true, false, "请从新登录!");
			return result.toJson();
		}
		map.put("id", id);
		ListResult<StockGoodsVM> ls = saleService.loadProductListByBillId(map);
		return ls.toJson();

	}

	/**
	 * 创建一个单据
	 * 
	 * @param billType
	 * @param u
	 * @return
	 */
	private SaleBillVM newSaleBill(Integer billType, UserVM u) {
		SaleBillVM bill = new SaleBillVM();

		Date ct = Calendar.getInstance().getTime();

		bill.setSaleTime(ct);

		Map<String, Object> billNoMap = GeneralUtil.getSerialNoPars(billType);
		String billNo = billSerialNoService.getNextBillSerialNo(billNoMap);

		bill.setSerialNo(billNo);
		bill.setPayId(0);
		bill.setId(0);
		bill.setOrgId(u.getOrgId());
		bill.setOrgName(u.getOrgName());

		bill.setRecTime(ct);
		bill.setPreparerOrgName(u.getOrgName());
		bill.setPreparerName(u.getName());
		// bill.setState(0);

		return bill;
	}

	@RequestMapping(value = "saveSaleBill.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String saveSaleBill(
			@RequestParam(value = "bill", required = false) String billJson,
			HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");

		Result<SaleBillVM> result = null;

		if (user == null) {
			result = new Result<SaleBillVM>(null, false, true, false, "请从新登录");
			return result.toJson();
		}

		try {
			JSONObject jObj = JSONObject.fromObject(billJson);

			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

			classMap.put("stockGoods", StockGoodsVM.class);

			SaleBillVM bill = (SaleBillVM) JSONObject.toBean(jObj,
					SaleBillVM.class, classMap);

			int custId = bill.getCustId();
			double goodsAmount = 0.0f;
			for (StockGoodsVM sg : bill.getStockGoods()) {
				if (sg.getGoodsId() > 0) {
					goodsAmount += sg.getGoodsPrice() * sg.getGoodsNumber();
				}
			}

			CustomerVM costomer = new CustomerVM();
			costomer = saleService.getCustomerInfoById(custId);

			if (costomer != null) {

				BigDecimal bd1 = new BigDecimal(goodsAmount);

				BigDecimal bd2 = costomer.getUsermoney();
				if (bd1.compareTo(bd2) < 0) {
					double subMoney = bd2.subtract(bd1).doubleValue();
					saleService.saveSaleBill(bill, user, subMoney);

					result = new Result<SaleBillVM>(bill);
				} else {
					result = new Result<SaleBillVM>(null, false, true, true,
							"当前选择用户，系统积分不足，不能完成支付，请先充值");
				}
			} else {
				result = new Result<SaleBillVM>(null, false, true, true,
						"获取对象失败，请重新登录后进行操作");
			}

		} catch (Exception ex) {
			result = new Result<SaleBillVM>(null, false, true, true,
					ex.getMessage());
		}
		return result.toJson();

	}

	@RequestMapping(value = "sendVerifCode.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String sendVerifCode(
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "custId", required = false) Integer custId,
			HttpServletRequest request) throws Exception {

		User user = (User) request.getSession().getAttribute("user");
		Result<String> result = new Result<String>();

		if (user == null) {
			result = new Result<String>(null, false, true, false, "请从新登录");
			return result.toJson();
		}

		if (mobile == null || "".equals(mobile) || custId == null
				|| custId == 0) {
			result.setIsSuccess(false);
			result.setMsg("手机号码或者客户id不能为空!");
			return result.toJson();
		}

		@SuppressWarnings("unchecked")
		Map<String, SmsInfo> smss = (Map<String, SmsInfo>) request.getSession()
				.getAttribute("verifCodes");

		if (smss == null) {
			smss = new HashMap<String, SmsInfo>();
		}

		try {
			String verifCode = GeneralUtil.createVerifCode();

			Sms sms = new Sms();
			sms.sendMessage(mobile, verifCode);

			SmsInfo smsInfo = new SmsInfo();
			smsInfo.setCustId(custId);
			smsInfo.setMobile(mobile);
			smsInfo.setSmsType(1);
			smsInfo.setVerifCode(verifCode);
			smsInfo.setSendTime(new Date());

			smss.put(verifCode, smsInfo);

			result.setIsSuccess(true);
			return result.toJson();
		} catch (Exception ex) {
			result.setIsSuccess(false);
			result.setMsg(ex.getMessage());
			return result.toJson();
		}
	}
}
