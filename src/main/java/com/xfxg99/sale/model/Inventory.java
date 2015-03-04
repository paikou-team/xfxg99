package com.xfxg99.sale.model;

import java.util.Date;

public class Inventory {
    private Integer id;

    private Integer orgId;

    private Integer goodsId;

    private Date updateTime;

    private Integer lastBillId;

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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLastBillId() {
        return lastBillId;
    }

    public void setLastBillId(Integer lastBillId) {
        this.lastBillId = lastBillId;
    }
}