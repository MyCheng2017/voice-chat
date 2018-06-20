package com.cheng.core;

public class ADPCM {

	private static int valPredLeft = 0;
	private static int valPredRight = 0;
	private static int indexLeft = 0;
	private static int indexRight = 0;
	private static int deValPredLeft = 0;
	private static int deValPredRight = 0;
	private static int deIndexLeft = 0;
	private static int deIndexRight = 0;
	/* 步长索引调整表 */
	private static int indexTable[] = { -1, -1, -1, -1, 2, 4, 6, 8, -1, -1, -1, -1, 2, 4, 6, 8 };
	//private static int indexTable[] = { -3, -2, -1, -1, 1, 2, 3, 4,-3, -2, -1, 0, 1, 2, 3, 4};
	/* 步长调整表 */
	private static int stepsizeTable[] = { 7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 21, 23, 25, 28, 31, 34, 37, 41, 45,
			50, 55, 60, 66, 73, 80, 88, 97, 107, 118, 130, 143, 157, 173, 190, 209, 230, 253, 279, 307, 337, 371, 408,
			449, 494, 544, 598, 658, 724, 796, 876, 963, 1060, 1166, 1282, 1411, 1552, 1707, 1878, 2066, 2272, 2499,
			2749, 3024, 3327, 3660, 4026, 4428, 4871, 5358, 5894, 6484, 7132, 7845, 8630, 9493, 10442, 11487, 12635,
			13899, 15289, 16818, 18500, 20350, 22385, 24623, 27086, 29794, 32767 };

	public static byte[] adpcm_coder(byte[] indata, int start, int len) {

		byte[] output = new byte[len / 4];
		/* 当前值 */
		int valLeft;
		int valRight;
		/* 当前值符号 */
		int signLeft;
		int signRight;
		/* 输出编码 */
		int deltaLeft;
		int deltaRight;
		/* 差值 */
		int diffLeft;
		int diffRight;
		/* 步长 */
		int stepLeft;
		int stepRight;
		/* Current change to valpred */
		int vpdiffLeft = 0;
		int vpdiffRight = 0;

		int k = 0;
		stepLeft = stepsizeTable[indexLeft];
		stepRight = stepsizeTable[indexRight];
 

		for (int i = start; i < len; i = i + 4) {
			valLeft = indata[i] << 8 | indata[i + 1];
			valRight = indata[i + 2] << 8 | indata[i + 3];
			/* 计算差值 */
			diffLeft = valLeft - valPredLeft;
			diffRight = valRight - valPredRight;
			/* 将符号和数值分离 */
			signLeft = (diffLeft < 0) ? 8 : 0;
			signRight = (diffRight < 0) ? 8 : 0;
			if (signLeft == 8)
				diffLeft = (-diffLeft);
			if (signRight == 8)
				diffRight = (-diffRight);

			deltaLeft = 0;
			deltaRight = 0;
			vpdiffLeft = (stepLeft >> 3);
			vpdiffRight = (stepRight >> 3);

			if (diffLeft >= stepLeft) {
				deltaLeft = 4;
				diffLeft -= stepLeft;
				vpdiffLeft += stepLeft;
			}
			if (diffRight >= stepRight) {
				deltaRight = 4;
				diffRight -= stepRight;
				vpdiffRight += stepRight;
			}
			stepLeft >>= 1;
			stepRight >>= 1;
			
			if (diffLeft >= stepLeft) {
				deltaLeft |= 2;
				diffLeft -= stepLeft;
				vpdiffLeft += stepLeft;
			}
			if (diffRight >= stepRight) {
				deltaRight |= 2;
				diffRight -= stepRight;
				vpdiffRight += stepRight;
			}
			stepLeft >>= 1;
			stepRight >>= 1;
			
			if (diffLeft >= stepLeft) {
				deltaLeft |= 1;
				vpdiffLeft += stepLeft;
			}
			if (diffRight >= stepRight) {
				deltaRight |= 1;
				vpdiffRight += stepRight;
			}
			/* Step 3 - Update previous value */
			if (signLeft == 8)
				valPredLeft -= vpdiffLeft;
			else
				valPredLeft += vpdiffLeft;
			if (signRight == 8)
				valPredRight -= vpdiffRight;
			else
				valPredRight += vpdiffRight;

			/* Step 4 - Clamp previous value to 16 bits */
			if (valPredLeft > 32767)
				valPredLeft = 32767;
			else if (valPredLeft < -32768)
				valPredLeft = -32768;
			if (valPredRight > 32767)
				valPredRight = 32767;
			else if (valPredRight < -32768)
				valPredRight = -32768;

			/* Step 5 - Assemble value, update index and step values */
			deltaLeft |= signLeft;
			deltaRight |= signRight;

			indexLeft += indexTable[deltaLeft];
			indexRight += indexTable[deltaRight];

			if (indexLeft < 0)
				indexLeft = 0;
			if (indexLeft > stepsizeTable.length - 1)
				indexLeft = stepsizeTable.length - 1;
			if (indexRight < 0)
				indexRight = 0;
			if (indexRight > stepsizeTable.length - 1)
				indexRight = stepsizeTable.length - 1;
			stepLeft = stepsizeTable[indexLeft];
			stepRight = stepsizeTable[indexRight];
			// System.err.println(deltaLeft + "==>" + deltaRight);
			// System.out.println(valLeft);
			byte out = (byte) (deltaLeft << 4 | deltaRight);
			// System.out.println(deltaLeft << 4| deltaRight);

			output[k++] = out;
			/*
			 * Step 6 - Output value if ( bufferstep ) { outputbuffer = (delta << 4) & 0xf0;
			 * } else { outp++ = (delta & 0x0f) | outputbuffer; } bufferstep = !bufferstep;
			 * }
			 */

			

		}
		return output;
	}

	public static byte[] adpcm_decoder(byte[] indata, int start, int len) {

		byte[] output = new byte[len * 4]; /* output buffer pointer */

		/* 当前值符号 */
		int signLeft;
		int signRight;

		/* 输入编码 */
		int deltaLeft;
		int deltaRight;

		/* 步长 */
		int stepLeft;
		int stepRight;

		/*当前调整值*/
		int vpdiffLeft = 0;
		int vpdiffRight = 0;

		stepLeft = stepsizeTable[deIndexLeft];
		stepRight = stepsizeTable[deIndexRight];

		int k = 0;
		
		for (int i = start; i < len; i++) {

			/* 获取编码 */
			deltaLeft = (indata[i] & 0xf0) >> 4;
			deltaRight = indata[i] & 0x0f;

			// System.out.println(deltaLeft+"==="+deltaRight);
			/* 获取步长索引 */
			deIndexLeft += indexTable[deltaLeft];
			deIndexRight += indexTable[deltaRight];

			if (deIndexLeft < 0)
				deIndexLeft = 0;
			if (deIndexLeft > stepsizeTable.length - 1)
				deIndexLeft = stepsizeTable.length - 1;
			if (deIndexRight < 0)
				deIndexRight = 0;
			if (deIndexRight > stepsizeTable.length - 1)
				deIndexRight = stepsizeTable.length - 1;

			/* 符号和数值分离 */
			signLeft = deltaLeft & 8;
			deltaLeft = deltaLeft & 7;
			signRight = deltaRight & 8;
			deltaRight = deltaRight & 7;
			// System.out.println(signLeft + "=sign=" + deltaLeft);
			

			vpdiffLeft = stepLeft >> 3;
			vpdiffRight = stepRight >> 3;
			if ((deltaLeft & 4) != 0)
				vpdiffLeft += stepLeft;
			if ((deltaLeft & 2) != 0)
				vpdiffLeft += stepLeft >> 1;
				//vpdiffLeft += stepLeft / 2;
			if ((deltaLeft & 1) != 0)
				vpdiffLeft += stepLeft >> 2;
				//vpdiffLeft += stepLeft/4;
			if ((deltaRight & 4) != 0)
				vpdiffRight += stepRight;
			if ((deltaRight & 2) != 0)
				vpdiffRight += stepRight >> 1;
				//vpdiffRight += stepRight /2;
			if ((deltaRight & 1) != 0)
				vpdiffRight += stepRight >> 2;
				//vpdiffRight += stepRight /4;

			if (signLeft == 8)
				deValPredLeft -= vpdiffLeft;
			else
				deValPredLeft += vpdiffLeft;
			if (signRight == 8)
				deValPredRight -= vpdiffRight;
			else
				deValPredRight += vpdiffRight;

			
			if (deValPredLeft > 32767)
				deValPredLeft = 32767;
			else if (deValPredLeft < -32768)
				deValPredLeft = -32768;
			if (deValPredRight > 32767)
				deValPredRight = 32767;
			else if (deValPredRight < -32768)
				deValPredRight = -32768;

			
			stepLeft = stepsizeTable[deIndexLeft];
			stepRight = stepsizeTable[deIndexRight];
			

			// System.out.println(valPredLeft + ">>>" + valPredRight);
			output[k++] = (byte) (deValPredLeft >> 8);
			output[k++] = (byte) (deValPredLeft & 0xff);

			output[k++] = (byte) (deValPredRight >> 8);
			output[k++] = (byte) (deValPredRight & 0xff);
		}
		return output;

	}

	public static void main(String[] args) {

		byte[] data = { 1, 1, 0, 20, 1, 15, 0, 28, 1, 25, 0, 5, 1, 36, 0, 28 };
		for (int i = 0; i < data.length; i = i + 4) {
			byte[] p = new byte[4];
			p[0] = 0;
			p[1] = 0;
			p[2] = data[i];
			p[3] = data[i + 1];
			System.out.print(byte2Int(p) + "  ");
		}
		System.out.println();
		byte[] d = adpcm_coder(data, 0, data.length);

		for (int i = 0; i < d.length; i++) {
			System.out.print(d[i] + "  ");
		}
		System.out.println();
		byte[] data2 = { 119, 116, 125, 115 };
		byte[] dd = adpcm_decoder(data2, 0, data2.length);
		for (int i = 0; i < dd.length; i = i + 4) {
			byte[] p = new byte[4];
			p[0] = 0;
			p[1] = 0;
			p[2] = dd[i];
			p[3] = dd[i + 1];
			System.out.println(byte2Int(p));
		}

		byte b = (byte) (-121 + 256);
		// System.err.println(b);

		byte[] i = int2Byte(-32761);
		i[0] = 0;
		i[1] = 0;
		int j = byte2Int(i);
		// System.err.println(j);

	}

	public static byte[] int2Byte(int value) {
		byte[] src = new byte[4];
		src[0] = (byte) ((value >> 24) & 0xFF);
		src[1] = (byte) ((value >> 16) & 0xFF);
		src[2] = (byte) ((value >> 8) & 0xFF);
		src[3] = (byte) (value & 0xFF);
		return src;
	}

	public static int byte2Int(byte[] bArr) {
		if (bArr.length != 4) {
			return -1;
		}
		return (int) ((((bArr[0] & 0xff) << 24) | ((bArr[1] & 0xff) << 16) | ((bArr[2] & 0xff) << 8)
				| ((bArr[3] & 0xff) << 0)));
	}
}
