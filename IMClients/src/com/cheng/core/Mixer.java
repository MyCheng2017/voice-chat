package com.cheng.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Mixer {
	private static byte[] bg = new byte[1024 * 10000];
	private static int p = 0;//下标

	public static void changer(String path) throws IOException {//更换背景音乐
		FileInputStream in = null;
		BufferedInputStream bin = null;
		try {
			File file = new File(path);
			in = new FileInputStream(file);
			bin = new BufferedInputStream(in);
			bin.read(bg, 0, bg.length);
		} finally {
			if (bin != null) {
				bin.close();
			}
			if (in != null) {
				in.close();
			}
		}
	}

	public static byte[] mix(byte[] data) {//混音实现
		byte[] out = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			out[i] = (byte)((data[i] + bg[p]) / 2);//相加求平均
			p++;
			if (p >= 1024 * 10000) {//避免溢出，实现背景循环
				p = 0;
			}
		}
		return out;
	}
}
