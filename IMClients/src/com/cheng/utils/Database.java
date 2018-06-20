package com.cheng.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import com.google.gson.Gson;

public class Database {
	private static HashMap<String, String> data = new HashMap<String, String>();
	Gson gson = new Gson();

	@SuppressWarnings("unchecked")
	private Database() {

		File file = new File("data.cheng");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileInputStream fin = null;
		BufferedInputStream in = null;
		try {
			fin = new FileInputStream(file);
			in = new BufferedInputStream(fin);
			byte[] buff = new byte[in.available()];
			int len = in.read(buff);
			data = gson.fromJson(new String(buff, 0, len, "utf-8"), HashMap.class);
			if (data == null) {
				data = new HashMap<String, String>();
				save();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	}

	private static class Holder {
		private static final Database DATA = new Database();
	}

	/**
	 * 获取配置列表
	 * 
	 * @return AppProperties实例
	 */
	public static Database getInstance() {
		return Holder.DATA;
	}

	public static void main(String[] args) {
		Database properties = getInstance();
		properties.putString("g", "9981");
		System.out.println(properties.getObject("o", HashMap.class));
	}

	/**
	 * 获取配置列表中key对应的int值
	 * 
	 * @param key
	 *            数值对应的key
	 * @param val
	 *            不存在则返回val
	 * @return 存在则返回对应的值，不存在返回val
	 */
	public int getInt(String key, int val) {
		String value = data.get(key);
		if (value != null && (!"".equals(value))) {
			return new Integer(value);
		} else {
			return val;
		}

	}

	/**
	 * 将对应的int值存储到 
	 * @param key
	 * @param val
	 */
	public void putInt(String key, int val) {
		data.put(key, val + "");
		save();
	}

	public long getLong(String key, long val) {
		String value = data.get(key);
		if (value != null && (!"".equals(value))) {
			return new Long(value);
		} else {
			return val;
		}

	}

	public void putLong(String key, long val) {
		data.put(key, val + "");
		save();
	}

	public String getString(String key, String val) {
		String value = data.get(key);
		if (value != null) {
			return value;
		} else {
			return val;
		}
	}

	public void putObject(String key, Object val) {
		data.put(key, gson.toJson(val));
		save();
	}

	public Object getObject(String key, Class<?> c) {
		String value = data.get(key);
		if (value != null) {
			return gson.fromJson(value, c);
		} else {
			return null;
		}
	}

	public void putString(String key, String val) {
		data.put(key, val + "");
		save();
	}

	public void clear() {
		data.clear();
		save();
	}

	public void remove(String key) {
		if (data.containsKey(key)) {
			data.remove(key);
			save();
		}
	}

	private void save() {
		String json = gson.toJson(data);
		File file = new File("data.cheng");
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			byte[] buff = json.getBytes();

			bos.write(buff);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
