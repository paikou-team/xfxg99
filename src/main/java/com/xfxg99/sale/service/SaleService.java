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

import com.xfxg99.core.GeneralUtil;
import com.xfxg99.core.ListResult;
import com.xfxg99.sale.dao.BillSerialNoMapper; 
import com.xfxg99.sale.dao.SaleBillMapper;
import com.xfxg99.sale.dao.SaleGoodsMapper;
import com.xfxg99.sale.model.OrderInfo;
import com.xfxg99.sale.model.SaleBill;
import com.xfxg99.sale.model.SaleGoods;
import com.xfxg99.sale.viewmodel.SaleBillVM;
import com.xfxg99.sale.viewmodel.StockGoodsVM;

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
	 */
	@Transactional
	public void saveSaleBill(SaleBillVM bill) {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> billNoMap = GeneralUtil.getSerialNoPars(2);

		double goodsAmount = 0.0f;
		int saleId = 0;
		int goodId = 0;
		int custId = 0;
		if (bill.getId() == 0) {
			for (StockGoodsVM sg : bill.getStockGoods()) {
				if (sg.getGoodsId() > 0) {
					goodId = sg.getGoodsId();
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
			// saleBillMapper.insert(bill);
			saleBillMapper.insert(bs);
			saleId = bs.getId();
			bill.setSerialNo(billSerialNoMapper.updateBillSerialNo(billNoMap));
		} else {
			saleBillMapper.updateByPrimaryKey(bill);
			map.put("saleId", bill.getId());
			List<Integer> ids = new ArrayList<Integer>();

			for (StockGoodsVM sg : bill.getStockGoods()) {
				ids.add(sg.getId());
			}
			map.put("ids", ids);
			saleBillMapper.deleteByNotExistId(map);
		}

		for (StockGoodsVM sg : bill.getStockGoods()) {
			sg.setStockId(bill.getId());
			if (sg.getId() == 0) {
				SaleGoods gs = new SaleGoods();
				gs.setId(0);
				gs.setSaleId(saleId);
				gs.setGoodsId(goodId);
				sg.getGoodsNumber();
				gs.setGoodsNumber(sg.getGoodsNumber());
				gs.setMarketPrice(sg.getMarketPrice());
				gs.setGoodsPrice(sg.getGoodsPrice());
				saleGoodsMapper.insert(gs);
			}
		}

		saveOrderInfo(custId, goodsAmount);
		saveAccountLog(custId, goodsAmount);
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
		map.put("userMoney", goodsAmount);
		map.put("frozenMoney", 0.00);
		map.put("rankPoints", 0);
		map.put("payPoints", 0);
		map.put("changeTime", changeTime);
		map.put("changeDesc", "用户消费记录，消费金额" + goodsAmount);
		map.put("changeType", 99);
		saleBillMapper.insertAccountLog(map);
	}

	private void saveOrderInfo(int custId, double goodsAmount) {
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
		order.setOrderStatus(false);
		order.setShippingStatus(false);
		order.setPayStatus(false);
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
		order.setPayId((byte) 4);
		order.setPayName("支付宝");
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
	}

	public ListResult<StockGoodsVM> loadProductListByBillId(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<StockGoodsVM> ls = saleGoodsMapper.loadProductListByBillId(map);
		int count = saleGoodsMapper.countByBillId(map);

		ListResult<StockGoodsVM> result = new ListResult<StockGoodsVM>(count,
				ls);

		return result;
	}
}
