package com.xfxg99.sale.viewmodel;

import com.xfxg99.sale.model.Inventory;

public class InventoryVM extends Inventory{


	private String orgName;
	private String goodsName;
	private Integer currentNumber; 
	public Integer getCurrentNumber() {
		return currentNumber;
	}
	public void setCurrentNumber(Integer currentNumber) {
		this.currentNumber = currentNumber;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
}
