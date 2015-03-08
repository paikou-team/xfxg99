package com.xfxg99.core;

import java.util.List;

import net.sf.json.JSONObject;

public class ListResult<T> {
	private boolean isSuccess=true;
	private String msg;
	private int total;
	private List<T> rows;
	private boolean isTimeOut;
	private boolean isSessionExpired;
	
	public ListResult()
	{
		
	}
	
	
	
	public ListResult(int total,List<T> rows){
		this.total=total;
		this.rows=rows;
		this.isSuccess=true;
	}
	
	public ListResult(int total,List<T> rows,boolean isSuccess){
		this.total=total;
		this.rows=rows;
		this.isSuccess=isSuccess;
	}
	
	public String toJson(){
		JSONObject rs=JSONObject.fromObject(this);
		return rs.toString();
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}



	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}



	public boolean getIsTimeOut() {
		return isTimeOut;
	}



	public void setIsTimeOut(boolean isTimeOut) {
		this.isTimeOut = isTimeOut;
	}



	public boolean getIsSessionExpired() {
		return isSessionExpired;
	}



	public void setIsSessionExpired(boolean isSessionExpired) {
		this.isSessionExpired = isSessionExpired;
	}
}
