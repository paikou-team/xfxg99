package com.xfxg99.sale.viewmodel;

import com.xfxg99.sale.model.StockBill;

public class StockBillVM  extends StockBill{
	private String sendOrgName;
	private String receiveOrgName;
	private String preparerName;
	
	public String getSendOrgName() {
		return sendOrgName;
	}
	public void setSendOrgName(String sendOrgName) {
		this.sendOrgName = sendOrgName;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	public String getPreparerName() {
		return preparerName;
	}
	public void setPreparerName(String preparerName) {
		this.preparerName = preparerName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	private String receiverName;
	
	
}
