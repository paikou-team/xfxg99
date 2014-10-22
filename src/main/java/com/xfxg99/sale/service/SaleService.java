package com.xfxg99.sale.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xfxg99.base.model.User;
import com.xfxg99.base.viewmodel.CustomerVM;
import com.xfxg99.core.GeneralUtil;
import com.xfxg99.core.ListResult;
import com.xfxg99.sale.dao.BillSerialNoMapper;
import com.xfxg99.sale.dao.SaleBillMapper;
import com.xfxg99.sale.dao.SaleGoodsMapper;
import com.xfxg99.sale.model.OrderAction;
import com.xfxg99.sale.model.OrderGoods;
import com.xfxg99.sale.model.OrderInfo;
import com.xfxg99.sale.model.PayLog;
import com.xfxg99.sale.model.SaleBill;
import com.xfxg99.sale.model.SaleGoods;
import com.xfxg99.sale.viewmodel.SaleBillVM;
import com.xfxg99.sale.viewmodel.SaleGoodsVM;

@Service("saleService")
public class SaleService {

	@Resource(name = "saleBillMapper")
	private SaleBillMapper saleBillMapper;

	@Resource(name = "saleGoodsMapper")
	private SaleGoodsMapper saleGoodsMapper;

	@Resource(name = "billSerialNoMapper")
	private BillSerialNoMapper billSerialNoMapper;

	public ListResult<SaleBillVM> loadListWithPage(Map<String, Object> map) {
		int total = saleBillMapper.countByVMMap(map);
		List<SaleBillVM> ls = saleBillMapper.loadListWithPage(map);

		return new ListResult<SaleBillVM>(total, ls);
	}

	public SaleBill selectByPrimaryKey(Integer id) {
		return saleBillMapper.selectByPrimaryKey(id);
	}

	/**
	 * 保存库存单据
	 * 
	 * @param bill
	 * @param user
	 * @param subMoney
	 */
	@Transactional
	public void saveSaleBill(SaleBillVM bill, User user, double subMoney) {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> billNoMap = GeneralUtil.getSerialNoPars(2);

		double goodsAmount = 0.0f;
		List<SaleGoodsVM> glist = new ArrayList<SaleGoodsVM>();

		int saleId = 0;
		int custId = 0;
		if (bill.getId() == 0) {
			for (SaleGoodsVM sg : bill.getSaleGoods()) {
				if (sg.getGoodsId() > 0) {
					goodsAmount += sg.getGoodsPrice() * sg.getGoodsNumber();
				}

			}

			SaleBill bs = new SaleBill();
			custId = bill.getCustId();
			bs.setCustId(bill.getCustId());
			bs.setId(0);
			bs.setGoodsAmount(new BigDecimal(goodsAmount));
			bs.setOrgId(bill.getOrgId());
			bs.setPayId(1);
			bs.setSerialNo(bill.getSerialNo());
			bs.setSaleTime(bill.getSaleTime());
			bs.setRecTime(bill.getRecTime());
			bs.setPreparerOrgId(bill.getPreparerOrgId());
			bs.setPreparerId(bill.getPreparerId());
			bs.setDescription(bill.getDescription());
			// saleBillMapper.insert(bill);
			saleBillMapper.insert(bs);
			saleId = bs.getId();
			bill.setSerialNo(billSerialNoMapper.updateBillSerialNo(billNoMap));
		} else {
			saleBillMapper.updateByPrimaryKey(bill);
			map.put("saleId", bill.getId());
			List<Integer> ids = new ArrayList<Integer>();

			for (SaleGoodsVM sg : bill.getSaleGoods()) {
				ids.add(sg.getId());
			}
			map.put("ids", ids);
			saleBillMapper.deleteByNotExistId(map);
		}

		for (SaleGoodsVM sg : bill.getSaleGoods()) {
			sg.setSaleId(bill.getId());
			if (sg.getId() == 0) {
				SaleGoods gs = new SaleGoods();

				gs.setId(0);
				gs.setSaleId(saleId);
				gs.setGoodsId(sg.getGoodsId());
				// sg.getGoodsNumber();
				gs.setGoodsNumber(sg.getGoodsNumber());
				gs.setMarketPrice(sg.getMarketPrice());
				gs.setGoodsPrice(sg.getGoodsPrice());
				saleGoodsMapper.insert(gs);
				glist.add(sg);
			}
		}

		int orderid = saveOrderInfo(custId, goodsAmount);
		saveOrderGoods(orderid, glist);
		saveOrderAction(orderid, user);
		saveEcsUserInfo(subMoney, custId);
		saveEcsCustomerPayInfo(orderid, custId,goodsAmount);
		saveAccountLog(custId, goodsAmount);
	}

	private void saveEcsCustomerPayInfo(int orderid, int custId,
			double goodsAmount) {
		// TODO Auto-generated method stub
		PayLog plog = new PayLog();
		plog.setLogId(0);
		plog.setOrderId(orderid);
		java.math.BigDecimal bd1 = new java.math.BigDecimal(goodsAmount);
		plog.setOrderAmount(bd1);
		plog.setOrderType(true);
		plog.setIsPaid(true);
		saleBillMapper.saveEcsCustomerPayInfo(plog);
	}

	private void saveEcsUserInfo(double subMoney, int custId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", custId); 
		map.put("usermoney", subMoney);
		saleBillMapper.updateUserInfoById(map);
	}

	private void saveOrderAction(int orderid, User user) {
		// TODO Auto-generated method stub
		OrderAction orderaction = new OrderAction();
		orderaction.setActionId(0);
		orderaction.setOrderId(orderid);
		orderaction.setActionUser(user.getName());
		orderaction.setOrderStatus(true);
		orderaction.setShippingStatus(false);
		orderaction.setPayStatus(false);
		orderaction.setActionPlace(true);
		orderaction.setActionNote("用户购买");
		Calendar cal = Calendar.getInstance();
		String changeTime = Long.toString(cal.getTimeInMillis());
		if (changeTime.length() > 10) {
			changeTime = changeTime.substring(0, 10);
		}
		orderaction.setLogTime(Integer.parseInt(changeTime));

		saleBillMapper.insertOrderAction(orderaction);
	}

	private void saveOrderGoods(int orderid, List<SaleGoodsVM> glist) {
		// TODO Auto-generated method stub
		for (int i = 0; i < glist.size(); i++) {
			OrderGoods og = new OrderGoods();
			og.setRecId(0);
			og.setOrderId(orderid);
			og.setGoodsId(glist.get(i).getGoodsId());
			og.setGoodsName(glist.get(i).getGoodsName());
			int s = glist.get(i).getGoodsNumber();
			og.setGoodsNumber((short) s);
			og.setGoodsSn(" ");

			java.math.BigDecimal bd1 = new java.math.BigDecimal(glist.get(i)
					.getMarketPrice());
			og.setMarketPrice(bd1);

			java.math.BigDecimal bd2 = new java.math.BigDecimal(glist.get(i)
					.getGoodsPrice());
			og.setGoodsPrice(bd2);
			og.setGoodsAttr(" ");
			og.setSendNumber((short) 0);
			og.setIsReal(true);
			og.setExtensionCode(" ");
			og.setParentId(0);
			og.setIsGift((short) 0);
			og.setGoodsAttrId(" ");
			og.setProductId(0);
			saleBillMapper.insertOrderGoods(og);

		}
	}

	private void saveAccountLog(int custId, double goodsAmount) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		String changeTime = Long.toString(cal.getTimeInMillis());
		if (changeTime.length() > 10) {
			changeTime = changeTime.substring(0, 10);
		}
		map.put("userId", custId);
		map.put("userMoney", goodsAmount*-1);
		map.put("frozenMoney", 0.00);
		map.put("rankPoints", 0);
		map.put("payPoints", 0);
		map.put("changeTime", changeTime);
		map.put("changeDesc", "用户消费记录，消费金额" + goodsAmount);
		map.put("changeType", 99);
		saleBillMapper.insertAccountLog(map);
	}

	private int saveOrderInfo(int custId, double goodsAmount) {
		// TODO Auto-generated method stub
		OrderInfo order = new OrderInfo();
		order.setOrderId(0);
		order.setUserId(custId);
		Calendar cal = Calendar.getInstance();
		String changeTime = Long.toString(cal.getTimeInMillis());
		if (changeTime.length() > 10) {
			changeTime = changeTime.substring(0, 10);
		}
		order.setOrderSn(changeTime);
		order.setOrderStatus(true);
		order.setShippingStatus(true);
		order.setPayStatus(true);
		order.setConsignee("……");
		order.setCountry((short) 1);
		order.setProvince((short) 26);
		order.setCity((short) 322);
		order.setAddress("五福桥东路");
		order.setZipcode(" ");
		order.setTel(" ");
		order.setMobile(" ");
		order.setEmail(" ");
		order.setBestTime(changeTime);
		order.setSignBuilding(" ");
		order.setPostscript(" ");
		order.setShippingId((byte) 5);
		order.setShippingName("申通快递");
		order.setPayId((byte) 1);
		order.setPayName("积分支付");
		order.setHowOos("自提货");
		order.setHowSurplus(" ");
		order.setPackName(" ");
		order.setCardName(" ");
		order.setCardMessage(" ");
		order.setInvContent(" ");
		order.setInvoiceNo(" ");
		order.setInvPayee(" ");
		order.setInvType(" ");
		java.math.BigDecimal bd1 = new java.math.BigDecimal(goodsAmount);
		java.math.BigDecimal bd2 = new java.math.BigDecimal(0.00);
		order.setGoodsAmount(bd1);
		order.setShippingFee(bd2);
		order.setInsureFee(bd2);
		order.setPayFee(bd2);
		order.setPackFee(bd2);
		order.setCardFee(bd2);
		order.setMoneyPaid(bd2);
		order.setSurplus(bd2);
		order.setIntegral(0);
		order.setIntegralMoney(bd2);
		order.setBonus(bd2);
		order.setOrderAmount(bd1);
		order.setFromAd((short) 0);
		order.setReferer("本站");
		order.setAddTime(Integer.parseInt(changeTime));
		order.setConfirmTime(0);
		order.setPayTime(0);
		order.setShippingTime(0);
		order.setPackId((byte) 0);
		order.setCardId((byte) 0);
		order.setExtensionId(0);
		order.setToBuyer(" ");
		order.setPayNote(" ");
		order.setAgencyId((short) 0);
		order.setDistrict((short) 2724);
		order.setInvType(" ");
		order.setTax(bd2);
		order.setIsSeparate(false);
		order.setParentId(0);
		order.setDiscount(bd2);
		order.setBonusId((short) 0);
		order.setExtensionCode(" ");

		saleBillMapper.insertOrderInfo(order);
		int ordId = order.getOrderId();
		return ordId;

	}

	public ListResult<SaleGoodsVM> loadProductListByBillId(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SaleGoodsVM> ls = saleGoodsMapper.loadProductListByBillId(map);
		int count = saleGoodsMapper.countByBillId(map);

		ListResult<SaleGoodsVM> result = new ListResult<SaleGoodsVM>(count,
				ls);

		return result;
	}

	public CustomerVM getCustomerInfoById(int custId) {
		// TODO Auto-generated method stub
		return saleBillMapper.getCustomerInfoById(custId);
	}
	
	public SaleBillVM loadVMById(Integer id){
		return saleBillMapper.loadVMById(id);
	}
}
