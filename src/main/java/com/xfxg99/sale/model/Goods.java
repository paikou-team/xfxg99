package com.xfxg99.sale.model;

/**
 * 商品对象
 * @author Owen
 *
 */
public class Goods {
	private  int id;
	private String sn;
	private String name;
	private double marketPrice;
	private double shopPrice;
	
	public int getId( ) {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSn( ) {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getName( ) {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMarketPrice( ) {
		return marketPrice;
	}
	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public double getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(double shopPrice) {
		this.shopPrice = shopPrice;
	}
}
