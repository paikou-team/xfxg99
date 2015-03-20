package com.xfxg99.sale.viewmodel;

import java.util.List;

import com.xfxg99.sale.model.StockBill;

public class StockBillVM  extends StockBill{
	private String stockInOrgName;
	private String stockOutOrgName;
	private String preparerOrgName;
	private String preparerName;
	private String confirmerOrgName;
	private List<StockGoodsVM>  stockGoods;
	
	public String getPreparerOrgName() {
		return preparerOrgName;
	}
	public void setPreparerOrgName(String preparerOrgName) {
		this.preparerOrgName = preparerOrgName;
	}
	public String getConfirmerOrgName() {
		return confirmerOrgName;
	}
	public void setConfirmerOrgName(String confirmerOrgName) {
		this.confirmerOrgName = confirmerOrgName;
	}
	private String confirmerName;
	
	public String getStockInOrgName() {
		return stockInOrgName;
	}
	public void setStockInOrgName(String stockInOrgName) {
		this.stockInOrgName = stockInOrgName;
	}
	public String getStockOutOrgName() {
		return stockOutOrgName;
	}
	public void setStockOutOrgName(String stockOutOrgName) {
		this.stockOutOrgName = stockOutOrgName;
	}
	public String getPreparerName() {
		return preparerName;
	}
	public void setPreparerName(String preparerName) {
		this.preparerName = preparerName;
	}
	public String getConfirmerName() {
		return confirmerName;
	}
	public void setConfirmerName(String confirmerName) {
		this.confirmerName = confirmerName;
	}
	public List<StockGoodsVM> getStockGoods() {
		return stockGoods;
	}
	public void setStockGoods(List<StockGoodsVM> stockGoods) {
		this.stockGoods = stockGoods;
	}

}
