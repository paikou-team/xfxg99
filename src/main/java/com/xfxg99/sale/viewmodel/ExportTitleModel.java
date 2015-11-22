package com.xfxg99.sale.viewmodel;

public class ExportTitleModel {
	private Integer id;
	private String serialNo;
	private String stockInOrgName;
	private String stockOutOrgName;
	private String billDate;
	private String confirmState;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
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
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getConfirmState() {
		return confirmState;
	}
	public void setConfirmState(String confirmState) {
		this.confirmState = confirmState;
	}
}
