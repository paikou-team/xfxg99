package com.xfxg99.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GeneralUtil {

	static  String[] seed={"0","1","2","3","4","5","6","7","8","9"};
	
	static int verifCodeLength=5;
	
	public static String createVerifCode(){
		
		String result="";
		
		for(int i=0;i<verifCodeLength;i++){
			result +=Math.round(Math.random() * 9);
		}
		return result;
	}
	
	
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
	

	public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "出生时间大于当前时间!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;//注意此处，如果不加1的话计算结果是错误的
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }

        return age;
    }
	
}
