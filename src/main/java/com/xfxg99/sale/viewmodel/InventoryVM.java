package com.xfxg99.sale.viewmodel;

import com.xfxg99.sale.model.Inventory;

public class InventoryVM extends Inventory{


	private String orgName;
	private String goodsName;
	private Integer currentNumber; 
	private Integer saleNumber;
	private Integer stkOutNumber;
	private Integer stkDbNumber;
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
	public Integer getSaleNumber() {
		return saleNumber;
	}
	public void setSaleNumber(Integer saleNumber) {
		this.saleNumber = saleNumber;
	}
	public Integer getStkOutNumber() {
		return stkOutNumber;
	}
	public void setStkOutNumber(Integer stkOutNumber) {
		this.stkOutNumber = stkOutNumber;
	}
	public Integer getStkDbNumber() {
		return stkDbNumber;
	}
	public void setStkDbNumber(Integer stkDbNumber) {
		this.stkDbNumber = stkDbNumber;
	}
	
}
