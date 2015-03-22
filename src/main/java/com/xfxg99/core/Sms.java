package com.xfxg99.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Sms {
	
	private String address;
	private String sn;
	private String pwd;
	
	public Sms() throws IOException{
		loadProperties();
	}
	
	
	private void loadProperties() throws IOException{
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream("/properties/sms.properties");
		prop.load(in);

		this.address=prop.getProperty("address");
		this.sn=prop.getProperty("sn");
		this.pwd=prop.getProperty("pwd");
		
		
	}
	
	public void sendMessage(String mobile,String msg){
		
	}
}
