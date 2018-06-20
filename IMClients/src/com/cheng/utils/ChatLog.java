package com.cheng.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import com.cheng.domain.IMMessage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * ���������¼
 * 
 * @author ChengAcer
 *
 */

public class ChatLog {

	static Gson gson = new Gson();

	public static void main(String[] args) throws IOException {
		String path = "chatlog/10001.cheng";
read(path);
	}

	public static LinkedList<IMMessage> insertFromUser(IMMessage msg) throws IOException {
		String path = "chatlog/" + msg.fromUser + ".cheng";
		LinkedList<IMMessage> data = read(path);
		data.add(msg);
		String json = gson.toJson(data);
		save(json, path);
		return data;

	}

	public static LinkedList<IMMessage> insertToUser(IMMessage msg) throws IOException {
		String path = "chatlog/" + msg.toUser + ".cheng";
		LinkedList<IMMessage> data = read(path);
		data.add(msg);
		String json = gson.toJson(data);
		save(json, path);
		return data;

	}

	public static LinkedList<IMMessage> read(String path) throws IOException {
		LinkedList<IMMessage> data = null;
		File file = new File(path);
		
		if (!file.exists()) {
			file.createNewFile();
		}
		FileInputStream fin = null;
		BufferedInputStream in = null;
		try {
			fin = new FileInputStream(file);
		//	new InputStreamReader(fin, "GBK");
		 
			in = new BufferedInputStream(fin);
			byte[] buff = new byte[in.available()];
			int len = in.read(buff);
			//System.out.println(len);gson.toJson(new LinkedList<IMMessage>())
			if (len == 0) {
				data = fromJsonList("[]", IMMessage.class);
			} else {
				data = fromJsonList(new String(buff, 0, len, "GBK"), IMMessage.class);
			}

			if (data == null) {
				data = new LinkedList<IMMessage>();
			}

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fin = null;
			}

		}
		return data;

	}

	public static <T> LinkedList<T> fromJsonList(String json, Class<T> cls) {
		Gson mGson = new Gson();
		LinkedList<T> mList = new LinkedList<T>();
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for (final JsonElement elem : array) {
			mList.add(mGson.fromJson(elem, cls));
		}
		return mList;
	}

	private static void save(String json, String path) throws IOException {

		File file = new File(path);
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			byte[] buff = json.getBytes();

			bos.write(buff,0,buff.length);
		} finally {
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
