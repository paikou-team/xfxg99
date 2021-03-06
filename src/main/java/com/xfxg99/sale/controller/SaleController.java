package com.xfxg99.sale.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.Organization;
import com.xfxg99.base.model.User;
import com.xfxg99.base.service.OrganizationService;
import com.xfxg99.base.viewmodel.CustomerVM;
import com.xfxg99.base.viewmodel.UserVM;

import com.xfxg99.sale.viewmodel.GoodsSaleVM;
import com.xfxg99.sale.viewmodel.SaleBillVM;

import com.xfxg99.sale.viewmodel.SaleGoodsVM;

import com.xfxg99.core.GeneralUtil;

import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result;
import com.xfxg99.core.Sms;
import com.xfxg99.core.SmsInfo;
import com.xfxg99.sale.model.Recharge;
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

	@Resource(name = "organizationService")
	protected OrganizationService organizationService;

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
		int saletype = joQuery.getInt("saletype");
		int isdelivery = joQuery.getInt("isdelivery");

		Organization og = new Organization();
		og = organizationService.getOrganization(orgId);
		if (orgId > 0) {
			// if (!user.getIsAllDataPermission()) {
			map.put("orgPath", og.getPath());
			map.put("orgId", orgId);
			// }
		}

		String beginTime = joQuery.getString("beginTime");
		String endTime = joQuery.getString("endTime");
		if (endTime != null && endTime != " " && endTime.length() > 0)
			endTime += " 23:59:59";
		String serialNo = joQuery.getString("serialNo");

		page = page == 0 ? 1 : page;

		map.put("saletype", saletype);
		if (isdelivery > 0) {
			map.put("isdelivery", isdelivery);
		}
		if (!"".equals(beginTime)) {
			map.put("beginDate", beginTime);
		}
		if (!"".equals(beginTime)) {
			map.put("endDate", endTime);
		}
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("serialNo", serialNo);

		ListResult<SaleBillVM> ls = saleService.loadListWithPage(map);

		return ls.toJson();
	}

	/**
	 * 获取所有销售商品的记录数据列表
	 * 
	 * @param query
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "loadGoodsSaleList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadGoodsSaleList(
			@RequestParam(value = "orgId", required = false) Integer orgId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) {
		try {
			Result<GoodsSaleVM> result = new Result<GoodsSaleVM>();
			UserVM user = (UserVM) request.getSession().getAttribute("user");

			Map<String, Object> map = new HashMap<String, Object>();
			int pageBegin = rows * (page > 0 ? (page - 1) : 0);
			if (user == null) {
				result = new Result<GoodsSaleVM>(null, false, true, false,
						"请从新登录!");
				return result.toJson();
			} 
				Organization og = new Organization();
				og = organizationService.getOrganization(orgId);
				map.put("orgPath", og.getPath()); 
			
			map.put("pageStart",pageBegin);
			map.put("pageSize", rows);
			map.put("orgId", orgId);

			ListResult<GoodsSaleVM> ls = saleService.loadGoodsSaleList(map);  
			 
			return ls.toJson();
		} catch (Exception ex) {
			Result<GoodsSaleVM> result = new Result<GoodsSaleVM>();
			result = new Result<GoodsSaleVM>(null, false, false, false, "请从新登录!");
			return result.toJson();
		}
	}

	@RequestMapping(value = "getTotalPriceInfo.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getTotalPriceInfo(
			@RequestParam(value = "saleQuery", required = false) String query,
			HttpServletRequest request) {

		Result<SaleBill> result = new Result<SaleBill>();
		try {
			UserVM user = (UserVM) request.getSession().getAttribute("user");

			if (user == null) {
				result = new Result<SaleBill>(null, false, true, false,
						"请从新登录!");
				return result.toJson();
			}

			JSONObject joQuery = JSONObject.fromObject(query);
			Map<String, Object> map = new HashMap<String, Object>();

			int orgId = joQuery.getInt("orgId");
			int saletype = joQuery.getInt("saletype");
			int isdelivery = joQuery.getInt("isdelivery");

			Organization og = new Organization();
			og = organizationService.getOrganization(orgId);

			// if (!user.getIsAllDataPermission()) {
			map.put("orgPath", og.getPath());
			map.put("orgId", orgId);
			// }

			String beginTime = joQuery.getString("beginTime");
			String endTime = joQuery.getString("endTime");
			if (endTime != null && endTime != " " && endTime.length() > 0)
				endTime += " 23:59:59";
			String serialNo = joQuery.getString("serialNo");

			map.put("saletype", saletype);
			if (isdelivery > 0) {
				map.put("isdelivery", isdelivery);
			}
			if (!"".equals(beginTime)) {
				map.put("beginDate", beginTime);
			}
			if (!"".equals(beginTime)) {
				map.put("endDate", endTime);
			}
			map.put("serialNo", serialNo);

			List<SaleBillVM> ls = saleService.loadTotalListWithPage(map);
			double totalPrice = 0;

			if (ls.size() > 0) {
				for (SaleBillVM sb : ls) {
					if (sb.getGoodsAmount() != null) {
						totalPrice += sb.getGoodsAmount().doubleValue();
					}
				}
			}
			BigDecimal b = new BigDecimal(totalPrice);
			double f = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			String totalp = Double.toString(f);
			result = new Result<SaleBill>(null, true, false, false, totalp);
			return result.toJson();
		} catch (Exception ex) {
			result = new Result<SaleBill>(null, true, false, false, "");
			return result.toJson();
		}
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
			bill = saleService.loadVMById(id);
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
		ListResult<SaleGoodsVM> ls = saleService.loadProductListByBillId(map);
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
		bill.setPreparerOrgId(u.getOrgId());
		bill.setPreparerOrgName(u.getOrgName());
		bill.setPreparerId(u.getId());
		bill.setPreparerName(u.getName());

		return bill;
	}

	// 现金支付方式保存，不进行验证码验证
	@RequestMapping(value = "saveCashSaleBill.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String saveCashSaleBill(
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
			classMap.put("saleGoods", SaleGoodsVM.class);

			SaleBillVM bill = (SaleBillVM) JSONObject.toBean(jObj,
					SaleBillVM.class, classMap);

			result = new Result<SaleBillVM>(null);

			int custId = bill.getCustId();
			CustomerVM Customer = new CustomerVM();
			Customer = saleService.getCustomerInfoById(custId);

			if (Customer == null) {
				result.setIsSuccess(false);
				result.setMsg("客户信息已经丢失,请联系系统管理员");
				return result.toJson();
			}

			BigDecimal bd2 = Customer.getUsermoney();

			BigDecimal bd1 = new BigDecimal(bill.calcTotal());

			double subMoney = bd2.subtract(bd1).doubleValue();
			bill.setSaletype(2);
			saleService.saveSaleBill(bill, user, subMoney);
			result = new Result<SaleBillVM>(bill);

		} catch (Exception ex) {
			result = new Result<SaleBillVM>(null, false, true, true,
					ex.getMessage());
		}
		return result.toJson();
	}

	@RequestMapping(value = "saveSaleBill.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String saveSaleBill(
			@RequestParam(value = "bill", required = false) String billJson,
			@RequestParam(value = "verifCode", required = false) String verifCode,

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
			classMap.put("saleGoods", SaleGoodsVM.class);

			SaleBillVM bill = (SaleBillVM) JSONObject.toBean(jObj,
					SaleBillVM.class, classMap);

			@SuppressWarnings("unchecked")
			Map<String, SmsInfo> smss = (Map<String, SmsInfo>) request
					.getSession().getAttribute("verifCodes");

			result = new Result<SaleBillVM>(null);

			if (smss == null) {
				result.setIsSuccess(false);
				result.setMsg("没有正确的验证码!");
			} else {
				if (!smss.containsKey(bill.getCustomerPhone())) {
					result = new Result<SaleBillVM>(bill);
					result.setIsSuccess(false);
					result.setMsg("没有验证码!");
				} else {
					SmsInfo smsInfo = smss.get(bill.getCustomerPhone());
					if (!smsInfo.getVerifCode().equals(verifCode)) {
						result = new Result<SaleBillVM>(bill);
						result.setIsSuccess(false);
						result.setMsg("验证码错误");
					} else {
						Date cdate = new Date();
						long interval = cdate.getTime()
								- smsInfo.getSendTime().getTime();

						int custId = bill.getCustId();

						if (interval / 1000 > 5 * 60) { // 验证码5发分钟内有效
							result.setIsSuccess(false);
							result.setMsg("验证码已经过期!");
							smss.remove(bill.getCustomerPhone());
						} else {

							CustomerVM Customer = new CustomerVM();
							Customer = saleService.getCustomerInfoById(custId);

							if (Customer == null) {
								result.setIsSuccess(false);
								result.setMsg("客户信息已经丢失,请联系系统管理员");
								return result.toJson();
							}

							BigDecimal bd2 = Customer.getUsermoney();// 李强，你这个代码有漏洞，这行代码应该来讲应该放在事务中，否则在并发下会有问题。

							BigDecimal bd1 = new BigDecimal(bill.calcTotal());

							if (bd1.compareTo(bd2) < 0) {
								double subMoney = bd2.subtract(bd1)
										.doubleValue();
								bill.setSaletype(1);
								saleService.saveSaleBill(bill, user, subMoney);
								smss.remove(bill.getCustomerPhone());
								result = new Result<SaleBillVM>(bill);
							} else {
								result = new Result<SaleBillVM>(null, false,
										true, true, "用户积分余额不足，不能交易，请先充值");
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			result = new Result<SaleBillVM>(null, false, true, true,
					ex.getMessage());
		}
		return result.toJson();
	}

	/*
	 * 确认充值信息逻辑
	 */
	@RequestMapping(value = "confirmDelivery.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String confirmDelivery(
			@RequestParam(value = "id", required = true) Integer id,
			HttpServletRequest request) throws Exception {
		try {
			SaleBill bill = new SaleBill();
			HttpSession session = request.getSession();
			UserVM user = (UserVM) session.getAttribute("user");
			String message = "";
			boolean isSessionExpired = false;
			boolean isSuccess = false;
			boolean isTimeout = false;
			if (user != null) {
				bill = saleService.selectByPrimaryKey(id);
				if (bill != null) {
					bill.setIsdelivery(2);
					saleService.updateByPrimaryKey(bill);
					isSuccess = true;
					message = "提货确认，成功";
				} else {
					isTimeout = true;
					message = "获取数据失败";
				}
			} else {
				isSessionExpired = true;
				message = "Session过期，请重新登录";
			}

			Result<SaleBill> s = new Result<SaleBill>(bill, isSuccess,
					isSessionExpired, isTimeout, message);
			return s.toJson();
		} catch (Exception ex) {
			Result<SaleBill> s = new Result<SaleBill>(null, false, false,
					false, "调用后台方法出错");
			return s.toJson();
		}
	}

	@RequestMapping(value = "sendVerifCode.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String sendVerifCode(
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "custId", required = false) Integer custId,
			@RequestParam(value = "amount", required = false) Double amount,
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
			request.getSession().setAttribute("verifCodes", smss);
		}

		try {
			String verifCode = GeneralUtil.createVerifCode();

			Sms sms = new Sms();

			DecimalFormat fmt = new DecimalFormat("0.00");
			String amountFmt = fmt.format(amount);

			String message = "您消费了" + amountFmt + "元,验证码:" + verifCode;

			sms.sendMessage(mobile, message);

			SmsInfo smsInfo = new SmsInfo();
			smsInfo.setCustId(custId);
			smsInfo.setMobile(mobile);
			smsInfo.setSmsType(1);
			smsInfo.setVerifCode(verifCode);
			smsInfo.setSendTime(new Date());

			smss.put(mobile, smsInfo);

			result.setIsSuccess(true);
			return result.toJson();
		} catch (Exception ex) {
			result.setIsSuccess(false);
			result.setMsg(ex.getMessage());
			return result.toJson();
		}
	}
}
