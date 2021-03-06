package com.xfxg99.sale.viewmodel;

import java.util.List;

import com.xfxg99.sale.model.SaleBill;;

public class SaleBillVM  extends SaleBill{
	private String orgName;
	private String customerName;
	private String realname;
	private String customerEmail;
	private String customerPhone;
	private String preparerOrgName;
	private String preparerName;
	
	private List<SaleGoodsVM>  saleGoods;
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getPreparerOrgName() {
		return preparerOrgName;
	}
	public void setPreparerOrgName(String preparerOrgName) {
		this.preparerOrgName = preparerOrgName;
	}
	public String getPreparerName() {
		return preparerName;
	}
	public void setPreparerName(String preparerName) {
		this.preparerName = preparerName;
	}
	public List<SaleGoodsVM> getSaleGoods() {
		return saleGoods;
	}
	public void setSaleGoods(List<SaleGoodsVM> saleGoods) {
		this.saleGoods = saleGoods;
	}
	/**
	 * 合计金额
	 * @return
	 */
	public double calcTotal(){
		double amt=0.0d;
		if(this.saleGoods!=null && this.saleGoods.size()>0){
			for (SaleGoodsVM sg : this.saleGoods) {
				if (sg.getGoodsId() > 0) {
					amt += sg.getGoodsPrice() * sg.getGoodsNumber(); 
				} 
			}
		}
		return amt;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
}
