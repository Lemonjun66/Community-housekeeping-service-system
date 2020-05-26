package com.xy.utils;

import java.util.Calendar;

public class getTimeUtil {
	
	public static String getTime() {
		Calendar cal=Calendar.getInstance();    
		int y=cal.get(Calendar.YEAR);    
		int m=cal.get(Calendar.MONTH);    
		int d=cal.get(Calendar.DATE);
		String date = y + "年" + m + "月" + d + "日";
		return date;
	}


}
