package com.xfxg99.sale.model;

import java.math.BigDecimal;

public class StockGoods {
    private Integer id;

    private Integer stockId;

    private Integer goodsId;

    private Integer goodsNumber;

    private BigDecimal marketPrice;

    private BigDecimal goodsPrice;

    private BigDecimal allocationPrice;

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

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getAllocationPrice() {
        return allocationPrice;
    }

    public void setAllocationPrice(BigDecimal allocationPrice) {
        this.allocationPrice = allocationPrice;
    }
}