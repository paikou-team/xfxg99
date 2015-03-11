package com.xfxg99.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GeneralUtil {

	
	public static Map<String,Object> getSerialNoPars(Integer billType){
		Calendar  calendar=Calendar.getInstance();
		
		DateFormat fmt=new SimpleDateFormat("yyMMdd");
		String d=fmt.format(calendar.getTime());
		
		Integer billDate=Integer.valueOf(d);
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("billType", billType);
		map.put("billDate", billDate);
		
		return map;
	}
	

}
