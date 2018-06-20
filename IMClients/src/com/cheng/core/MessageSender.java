package com.cheng.core;

import java.io.IOException;
import java.util.List;

import com.cheng.main.App;
import com.google.gson.Gson;

/**
 * 消息发送类
 * 
 * @author ChengAcer
 *
 */
public class MessageSender {
	/**
	 * 验证是否符合通信协议，数据是否校验正确
	 * 
	 * @param b
	 *            数据
	 * @return 校验成功返回true；失败返回false
	 */
	
	
	protected boolean checkByte(byte[] b) {
		
		for (int i = 0; i < 5; i++) {
			System.out.print(b[i]+"-");
		}
		System.out.println("校验数据");
		int length = b.length;
		if (length < 5)
			return false;

		byte check = b[4];
		byte xor = (byte) (b[0] ^ b[1] ^ b[2] ^ b[3]);
		if (xor != check)
			return false;

		return true;

	}

	public void send(byte[] b, IMConnection conn) throws IOException {
		if (conn != null) {
			conn.writer.write(b);
		}
	}

	public void send(byte[] b) throws IOException {
		
		App.conn.writer.write(b);
	}

	public byte[] toProtocal(byte type, String json) {
		byte[] result;
		byte[] data = json.getBytes();
		int length = data.length;
		byte high = (byte) (length / 100);
		byte low = (byte) (length % 100);
		result = new byte[length + 5];
		result[0] = 55;
		result[1] = high;
		result[2] = low;
		result[3] = type;
		result[4] = (byte) (55 ^ high ^ low ^ type);
		for (int i = 5; i < data.length + 5; i++) {
			result[i] = data[i - 5];
		}
		return result;
	}

	public byte[] toProtocal(byte type, byte[] data) {
		byte[] result;

		int length = data.length;
		byte high = (byte) (length / 100);
		byte low = (byte) (length % 100);
		result = new byte[length + 5];
		result[0] = 55;
		result[1] = high;
		result[2] = low;
		result[3] = type;
		result[4] = (byte) (55 ^ high ^ low ^ type);
		for (int i = 5; i < data.length + 5; i++) {
			result[i] = data[i - 5];
		}
		return result;
	}

	public byte[] toProtocal(byte type, Object data) {
		return toProtocal(type, new Gson().toJson(data));
	}

	public byte[] toProtocal(byte type, List<Object> data) {
		return toProtocal(type, new Gson().toJson(data));
	}

}
