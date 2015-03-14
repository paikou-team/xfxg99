package com.xfxg99.sale.viewmodel;

import java.util.List;

import com.xfxg99.sale.model.SaleBill;;

public class SaleBillVM  extends SaleBill{
	private String orgName;
	private String customerName;
	private String customerEmail;
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
