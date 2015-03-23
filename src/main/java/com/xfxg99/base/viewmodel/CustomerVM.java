package com.xfxg99.base.viewmodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.xfxg99.core.GeneralUtil;
import com.xfxg99.sale.viewmodel.AddressVM;

public class CustomerVM {
	 
	private Integer id; 
	private String name;
	private String email;
	private String phone;
	private Integer sex;
	private Date birthday;
	private String recUser;
	private Integer orgId;
	private String orgName;
	private List<AddressVM>  addresses;
	
	private Integer age;//年龄，及时计算
	private Boolean isBless;//是否祝福过了
	private Integer period; //到期时间
	private Integer blessId;
	private String blessDescription;
	
	private BigDecimal usermoney;
	
	public Integer getAge() throws Exception {
		if(this.birthday ==null){
			return 0;
		}else{
			age=new Integer(GeneralUtil.getAge(birthday));
			return age;
		}
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getRecUser() {
		return recUser;
	}
	public void setRecUser(String recUser) {
		this.recUser = recUser;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Boolean getIsBless() {
		return isBless;
	}
	public void setIsBless(Boolean isBless) {
		this.isBless = isBless;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getBlessId() {
		return blessId;
	}
	public void setBlessId(Integer blessId) {
		this.blessId = blessId;
	}
	public String getBlessDescription() {
		return blessDescription;
	}
	public void setBlessDescription(String blessDescription) {
		this.blessDescription = blessDescription;
	}
	public BigDecimal getUsermoney() {
		return usermoney;
	}
	public void setUsermoney(BigDecimal usermoney) {
		this.usermoney = usermoney;
	}
	
}
