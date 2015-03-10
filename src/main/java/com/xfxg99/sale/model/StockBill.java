package com.xfxg99.sale.model;

import java.util.Date;

public class StockBill {
    private Integer id;

    private Integer billType;

    private String serialNo;

    private Date billTime;

    private Integer stockInOrgId;

    private Integer stockOutOrgId;

    private Integer state;

    private Integer preparerOrgId;

    private Integer preparerId;

    private Date prepareTime;

    private Integer confirmerOrgId;

    private Integer confirmerId;

    private Date confirmTime;

    private String description;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPreparerOrgId() {
        return preparerOrgId;
    }

    public void setPreparerOrgId(Integer preparerOrgId) {
        this.preparerOrgId = preparerOrgId;
    }

    public Integer getPreparerId() {
        return preparerId;
    }

    public void setPreparerId(Integer preparerId) {
        this.preparerId = preparerId;
    }

    public Date getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(Date prepareTime) {
        this.prepareTime = prepareTime;
    }

    public Integer getConfirmerOrgId() {
        return confirmerOrgId;
    }

    public void setConfirmerOrgId(Integer confirmerOrgId) {
        this.confirmerOrgId = confirmerOrgId;
    }

    public Integer getConfirmerId() {
        return confirmerId;
    }

    public void setConfirmerId(Integer confirmerId) {
        this.confirmerId = confirmerId;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}