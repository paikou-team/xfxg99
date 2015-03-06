package com.xfxg99.sale.model;

import java.util.Date;

public class StockBill {
    private Integer id;

    private String billType;

    private String serialNo;

    private Date billTime;

    private Integer sendOrgId;

    private Integer receiveOrgId;

    private Integer state;

    private Integer preparerId;

    private Integer receiverId;

    private Date prepareTime;

    private Date receiveTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
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

    public Integer getSendOrgId() {
        return sendOrgId;
    }

    public void setSendOrgId(Integer sendOrgId) {
        this.sendOrgId = sendOrgId;
    }

    public Integer getReceiveOrgId() {
        return receiveOrgId;
    }

    public void setReceiveOrgId(Integer receiveOrgId) {
        this.receiveOrgId = receiveOrgId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPreparerId() {
        return preparerId;
    }

    public void setPreparerId(Integer preparerId) {
        this.preparerId = preparerId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Date getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(Date prepareTime) {
        this.prepareTime = prepareTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}