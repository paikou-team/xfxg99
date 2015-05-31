package com.xfxg99.base.viewmodel;

import java.util.List;

import com.xfxg99.base.model.Organization;

public class OrganizationVM extends Organization {
	private List<OrganizationVM> children;
	private String state;
	private String text;
	public List<OrganizationVM> getChildren() {
		return children;
	}

	public void setChildren(List<OrganizationVM> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
