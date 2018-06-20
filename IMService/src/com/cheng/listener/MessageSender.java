package com.cheng.listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cheng.core.IMConnManager;
import com.cheng.core.IMConnection;
import com.cheng.domain.User;
import com.google.gson.Gson;

/**
 * 消息发送类
 * 
 * @author ChengAcer
 *
 */
public class MessageSender<T> {
	/**
	 * 验证是否符合通信协议，数据是否校验正确
	 * 
	 * @param b
	 *            数据
	 * @return 校验成功返回true；失败返回false
	 */
	protected boolean checkByte(byte[] b) {
		int length = b.length;
		if (length < 5)
			return false;
		byte check = b[4];
		byte xor = (byte) (b[0] ^ b[1] ^ b[2] ^ b[3]);
		if (xor != check)
			return false;
		return true;
	}

	public void toClient(byte[] b, IMConnection conn) throws IOException {
		if (conn != null) {
			conn.writer.write(b);
		}
	}

	public void toEveryClient(List<User> users, byte[] b) throws IOException {
		if (users == null || users.size() <= 0) {
			return;
		}
		HashMap<Long, IMConnection> conns = IMConnManager.conns;
		for (User u : users) {
			if (u != null) {
				long id = u.getId().longValue();
				if (conns.containsKey(id)) {
					conns.get(id).writer.write(b);
				}
			}
		}
	}

	public void toOtherClient(byte[] b) throws IOException {
		Set<Map.Entry<Long, IMConnection>> allOnLines = IMConnManager.conns.entrySet();
		for (Map.Entry<Long, IMConnection> entry : allOnLines) {
			if (entry.getValue().to != null) {
				entry.getValue().writer.write(b);
			}
		}
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
		System.err.println(high + "-" + low + "-" + type + "-" + result[4]);
		for (int i = 5; i < data.length + 5; i++) {
			result[i] = data[i - 5];
		}
		return result;
	}

	public byte[] toProtocal(byte type, Object data) {
		return toProtocal(type, new Gson().toJson(data));
	}

	public byte[] listToProtocal(byte type, List<?> list) {
		return toProtocal(type, new Gson().toJson(list));
	}

	public T getByByte(byte[] data, Class<T> c) {
		String json = null;
		json = new String(data, 5, data[1] * 100 + data[2]);
		Gson g = new Gson();
		return g.fromJson(json, c);
	}
}
