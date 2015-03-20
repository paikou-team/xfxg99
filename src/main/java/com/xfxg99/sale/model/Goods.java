package com.xfxg99.sale.model;

/**
 * 商品对象
 * @author Owen
 *
 */
public class Goods {
	private  int id;
	private int catId;
	private String catName;
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
	public int getCatId( ){
		return catId;
	}
	public void setCatId(int catId){
		this.catId = catId;
	}
	public String getCatName( ){
		return catName;
	}
	public void setCatName(String catName){
		this.catName = catName;
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
