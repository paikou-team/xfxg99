package com.xfxg99.sale.model;

import java.util.Date;

public class StockQuery {

	private Integer stockInOrgId;
	private Integer stockOutOrgId;
	private String serialNo;
	private Date beginTime;
	private Date endTime;
	
	public Integer getStockInOrgId() {
		return stockInOrgId;
	}
	public void setStockInOrgId(Integer stockInOrgId) {
		this.stockInOrgId = stockInOrgId;
	}
	public Integer getStockOutOrgId() {
		return stockOutOrgId;
	}
	public void setStockOutOrgId(Integer stockOutOrgId) {
		this.stockOutOrgId = stockOutOrgId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
