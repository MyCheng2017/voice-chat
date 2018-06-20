package com.cheng.utils;

import java.util.regex.Pattern;

public class Utils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	 public static boolean isInteger(String str) {  
	        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	        return pattern.matcher(str).matches();  
	  }
}
