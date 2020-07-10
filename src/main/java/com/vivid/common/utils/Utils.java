package com.vivid.common.utils;

import java.util.Calendar;
import java.util.Date;

public class Utils {
	private Utils() {}
	
	public static boolean sendCode(String ptn, String code) {
		String url = "http://api.weimi.cc/2/sms/send.html?uid=fFSqfrGYNbOd&pas=ep4kmy6w&mob=" + ptn + "&cid=jkd9uZ2SVxox&p1=" + code;
		
		String str = HttpConnectorHelper.httpGet(url);
		if(str.startsWith("0")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Date getMonthStart(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }
    /**
     * 获取本月最后一天
     * @return String
     * **/
    public static Date getMonthEnd(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    public static Date getWeekStart(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        return cal.getTime();
    }
    /**
     * 获取本周的最后一天
     * @return String
     * **/
    public static Date getWeekEnd(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);
        return cal.getTime();
    }
	public static void main(String[] args) {
		//sendCode("13816802100","1122");
	}
}
