package com.xfxg99.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;


public class Sms {
	
	private String address;
	private String sn;
	private String pwd;
	private String msg;
	private String sign;
	
	public Sms() throws IOException{
		loadProperties();
	}
	
	
	private void loadProperties() throws IOException{
		Properties prop = new Properties();
		prop.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("/properties/sms.properties"), "UTF-8")); 
		//prop.load(new InputStreamReader(new FileInputStream(new File("/properties/sms.properties"))));
		//InputStream in = getClass().getResourceAsStream("/properties/sms.properties");
		//prop.load(in);

		this.address= prop.getProperty("address");
		this.sn=prop.getProperty("sn");
		this.pwd=prop.getProperty("pwd");
		this.msg=prop.getProperty("msg");
		this.sign= prop.getProperty("sign");
		
	}
	
	public void sendMessage(String mobile,String msg) throws Exception{
		//"http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=SN&pwd=MD5(sn+password)&mobile=mobile&content=content&ext=&stime=&rrid=&msgfm";

		String pwdmd5=GeneralUtil.MD5(sn+pwd).toUpperCase();
		
		String msg2=msg +=" "+this.msg + " " + this.sign;
		//String msg2=msg + " " + "【幸福享购网】";
		
		msg2 = URLEncoder.encode(msg, "utf-8");
		
		String urlstr =address+"?sn=" + sn +"&pwd=" +pwdmd5 +"&mobile="+mobile +"&content="+msg2;
		
		URL smsUrl = new URL(urlstr);
	
		HttpURLConnection connection = (HttpURLConnection)smsUrl.openConnection();
		
		connection.connect();
        // 发送数据到服务器并使用Reader读取返回的数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String lines;
        String result="";
        while ((lines = reader.readLine()) != null) {
             result +=lines;
       }
       reader.close();
       
       // 断开连接
       connection.disconnect();
//       if(result!=null && StringUtils.isNumeric(result)){
//    	   
//    	   int state=Integer.parseInt(result);
//
//           if(state<0){
//        	   throw new Exception("发送短信失败，错误识别号码:"+state);
//           }
//       }
	}
}
