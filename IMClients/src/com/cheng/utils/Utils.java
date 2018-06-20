package com.cheng.utils;

import java.util.Calendar;

public class Utils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String getTime() {
		Calendar calendar = Calendar.getInstance();
		String date = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-"
				+ calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE)
				+ ":" + calendar.get(Calendar.SECOND);
		return date;
	}
	
}
