package com.xfxg99.base.model;

public class User {
    private Integer id;

    private String name;

    private String password;

    private Integer orgId;

    private String description;
    
    private boolean isused;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean getIsUsed(){
    	return  isused;
    }
    
    public void setIsUsed(boolean isUsed){
    	this.isused = isUsed;
    }
}