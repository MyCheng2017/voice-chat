package com.cheng.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Mixer {
	private static byte[] bg = new byte[1024 * 10000];
	private static int p = 0;//�±�

	public static void changer(String path) throws IOException {//������������
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

	public static byte[] mix(byte[] data) {//����ʵ��
		byte[] out = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			out[i] = (byte)((data[i] + bg[p]) / 2);//�����ƽ��
			p++;
			if (p >= 1024 * 10000) {//���������ʵ�ֱ���ѭ��
				p = 0;
			}
		}
		return out;
	}
}
