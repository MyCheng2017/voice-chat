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

public class Properties {
	private static HashMap<String, String> properties = new HashMap<String, String>();
	Gson gson = new Gson();

	@SuppressWarnings("unchecked")
	private Properties() {

		File file = new File("setting.cheng");
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
			properties = gson.fromJson(new String(buff, 0, len, "utf-8"), HashMap.class);
			if (properties == null) {
				properties = new HashMap<String, String>();
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
		private static final Properties PROPERTIES = new Properties();
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @return AppPropertiesʵ��
	 */
	public static Properties getInstance() {
		return Holder.PROPERTIES;
	}

	public static void main(String[] args) {
 
	}

	/**
	 * ��ȡ�����б���key��Ӧ��intֵ
	 * 
	 * @param key
	 *            ��ֵ��Ӧ��key
	 * @param val
	 *            �������򷵻�val
	 * @return �����򷵻ض�Ӧ��ֵ�������ڷ���val
	 */
	public int getInt(String key, int val) {
		String value = properties.get(key);
		if (value != null && (!"".equals(value))) {
			return new Integer(value);
		} else {
			return val;
		}

	}

	/**
	 * ����Ӧ��intֵ�洢��
	 * 
	 * @param key
	 * @param val
	 */
	public void putInt(String key, int val) {
		properties.put(key, val + "");
		save();
	}

	public long getLong(String key, long val) {
		String value = properties.get(key);
		if (value != null && (!"".equals(value))) {
			return new Long(value);
		} else {
			return val;
		}

	}

	public void putLong(String key, long val) {
		properties.put(key, val + "");
		save();
	}

	public String getString(String key, String val) {
		String value = properties.get(key);
		if (value != null) {
			return value;
		} else {
			return val;
		}
	}

	public void putObject(String key, Object val) {
		properties.put(key, gson.toJson(val));
		save();
	}

	public Object getObject(String key, Class<?> c) {
		String value = properties.get(key);
		if (value != null) {
			return gson.fromJson(value, c);
		} else {
			return null;
		}
	}

	public void putString(String key, String val) {
		properties.put(key, val + "");
		save();
	}

	public boolean getBoolean(String key, boolean val) {
		String value = properties.get(key);
		if (value != null) {
			return new Boolean(value);
		} else {
			return val;
		}
	}

	public void putBoolean(String key, boolean val) {
		properties.put(key, val + "");
		save();
	}

	public void clear() {
		properties.clear();
		save();
	}

	public void remove(String key) {
		if (properties.containsKey(key)) {
			properties.remove(key);
			save();
		}
	}

	private void save() {
		String json = gson.toJson(properties);
		File file = new File("setting.cheng");
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
