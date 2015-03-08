package com.xfxg99.sale.model;

import java.math.BigDecimal;

public class StockGoods {
    private Integer id;

    private Integer stockId;

    private Integer goodsId;

    private Integer goodsNumber;

    private double marketPrice;

    private  double goodsPrice;

    private  double allocationPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public double getAllocationPrice() {
        return allocationPrice;
    }

    public void setAllocationPrice(double allocationPrice) {
        this.allocationPrice = allocationPrice;
    }
}