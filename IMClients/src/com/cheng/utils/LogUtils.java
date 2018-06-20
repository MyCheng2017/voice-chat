package com.cheng.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ±£´æÁÄÌì¼ÇÂ¼
 * 
 * @author ChengAcer
 *
 */

public class LogUtils {

	public static void main(String[] args) {

	}

	public static void log(String key, String log) {
		try {
			Date d = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateNowStr = sdf.format(d);
			String value = dateNowStr + "    " + key + "  --->>>  " + log;
			System.err.println(value);
			save(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void save(String log) throws IOException {

		File file = new File("log.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		FileInputStream in = null;
		BufferedInputStream ins = null;

		try {
			in = new FileInputStream(file);
			ins = new BufferedInputStream(in);
			byte[] b = new byte[1024];
			int len = 0;
			StringBuffer sbuff = new StringBuffer();
			while ((len = ins.read(b)) != -1) {
				sbuff.append(b);
			}

			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(sbuff.toString().getBytes());
			byte[] buff = log.getBytes();
			bos.write(buff);
			bos.flush();
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ins = null;
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				bos = null;
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fos = null;
			}
		}

	}
}
