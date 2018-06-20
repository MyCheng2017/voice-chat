package com.cheng.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static void main(String[] args) {
		System.out.println(md5("123"));

	}

	public static String md5(String str) {
		StringBuffer buffer = new StringBuffer();
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes());
			byte[] bs = md5.digest();
			char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			int j = bs.length;
			for (int i = 0; i < j; i++) { // i = 0
				byte byte0 = bs[i]; // 95
				buffer.append(md5String[byte0 >>> 4 & 0xf]); // 5
				buffer.append(md5String[byte0 & 0xf]); // F
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
