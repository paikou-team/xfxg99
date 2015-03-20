package com.xfxg99.sale.model;

import java.math.BigDecimal;
import java.util.Date;

public class Recharge {
    private Integer id;

    private Integer orgId;

    private Integer custId;

    private BigDecimal money;

    private Date rechargeTime;

    private Date confirmTime;

    private Integer confirmUserId;
    
    private String rechargeDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(Integer confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

	public String getRechargedesc() {
		return rechargeDesc;
	}

	public void setRechargedesc(String rechargedesc) {
		this.rechargeDesc = rechargedesc;
	}
}