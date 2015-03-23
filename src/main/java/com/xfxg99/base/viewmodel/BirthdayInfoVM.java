package com.xfxg99.base.viewmodel;

import java.util.List;

import com.xfxg99.base.model.BirthdayInfo;
import com.xfxg99.base.viewmodel.CustomerVM;

public class BirthdayInfoVM {
	private CustomerVM customer;
	private List<BirthdayInfo>  infos;
	
	public CustomerVM getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerVM customer) {
		this.customer = customer;
	}
	public List<BirthdayInfo> getInfos() {
		return infos;
	}
	public void setInfos(List<BirthdayInfo> infos) {
		this.infos = infos;
	}
}
