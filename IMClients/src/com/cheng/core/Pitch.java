package com.cheng.core;

/**
 * ±äÉù
 * 
 * @author ChengAcer
 *
 */
public class Pitch {
	public static final int PITCH_SPEED_1 = 10;
	public static final int PITCH_SPEED_2 = 5;
	public static final int PITCH_SPEED_3 = 3;
	public static final int PITCH_SPEED_4 = 2;

	public static byte[] upPitch(byte[] in, int speed) {
		int len = in.length;
		byte[] out = new byte[24576];
		int o = 0;
		int p = 1;
		for (int i = 0; i < len; i = i + 4) {
			if (o+3>=out.length) {
				break;
			}
			out[o++] = in[i];
			out[o++] = in[i + 1];
			out[o++] = in[i + 2];
			out[o++] = in[i + 3];
			p++;
			if (p % speed == 0) {
				if (i+4>=len) {
					continue;
				}
				out[o++] = (byte) ((in[i]+in[i+4])/2);
				out[o++] = 0;
				out[o++] =  (byte) ((in[i]+in[i+4])/2);
				out[o++] =  0;
			}
			
		}
		return out;
	}

	public static byte[] downPitch(byte[] in, int speed) {
		int len = in.length;
		byte[] out = new byte[len];
		int o = 0;
		int p = 1;

		for (int i = 0; i < len; i = i + 4) {
			if (o+3>=out.length) {
				break;
			}
			if (p % speed != 0) {
				out[o++] = in[i];
				out[o++] = in[i + 1];
				out[o++] = in[i + 2];
				out[o++] = in[i + 3];
			}
			p++;
		}
		return out;
	}

	public static void main(String[] args) {
		int k = 0;
		for (int i = 0; i < 1024 * 60; i++) {
			k++;
			if (i % 2 == 0) {
				k--;
			}
		}

		System.out.println(k);
	}
}
