package com.xfxg99.core;

import java.util.Date;

public class SmsInfo {

	private Integer custId;
	private String mobile;
	private Integer smsType;
	private String message;
	private Date	sendTime;
	private String  verifCode;
	

	
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getSmsType() {
		return smsType;
	}
	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getVerifCode() {
		return verifCode;
	}
	public void setVerifCode(String verifCode) {
		this.verifCode = verifCode;
	}
	
	
}
