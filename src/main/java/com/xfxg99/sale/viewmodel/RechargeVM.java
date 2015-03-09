package com.xfxg99.sale.viewmodel;

import com.xfxg99.sale.model.Recharge;

public class RechargeVM extends Recharge {
 
	private String orgName;
	private String userName;
	private String custName;
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
}
