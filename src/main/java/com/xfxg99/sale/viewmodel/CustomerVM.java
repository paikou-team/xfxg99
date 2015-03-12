package com.xfxg99.sale.viewmodel;

import java.util.Date;
import java.util.List;

public class CustomerVM {
	
	private String id; 
	private String name;
	private String email;
	private String phone;
	private Date birthday;
	private List<AddressVM>  addresses;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public List<AddressVM> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressVM> addresses) {
		this.addresses = addresses;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
