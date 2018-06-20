package com.cheng.core;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.cheng.domain.User;
import com.cheng.listener.OnRecevieListener;

/**
 * 将socket封装成带有用户信息的类，并可以启动消息监听
 * 
 * @author ChengAcer
 *
 */
public class IMConnection extends Thread {

	private Socket socket = null;
	/**
	 * 连接输出流
	 */
	public DataOutputStream writer = null;
	/**
	 * 连接输入流
	 */
	public DataInputStream reader = null;
	/**
	 * 谁的连接
	 */
	public User who = null;
	/**
	 * 当前通信对象
	 */
	public User to = null;
	/**
	 * 连接的ip
	 */
	public String ip;
	/**
	 * 连接端口
	 */
	public int port;

	/**
	 * 从传进来的socket获取输入流和输出流
	 * 
	 * @param scoket
	 *            用户连接的socket
	 */
	public IMConnection(Socket scoket) {
		super();
		try {
			this.socket = scoket;
			writer = new DataOutputStream(this.socket.getOutputStream());
			reader = new DataInputStream(this.socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过ip和端口创建socket，并获取输入输出流
	 * 
	 * @param ip
	 * @param port
	 */
	public IMConnection(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
		init(ip, port);
	}

	private void init(String ip, int port) {
		try {
			this.socket = new Socket(ip, port);
			writer = new DataOutputStream(this.socket.getOutputStream());
			reader = new DataInputStream(this.socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 如果socket断开，重新连接并开始监听消息，否则直接监听消息
	 * 
	 */
	public void connect() {
		if (this.socket == null) {
			init(ip, port);
		}
		flag = true;
		start();
	}

	/**
	 * 断开连接
	 */
	public void disconnect() {
		try {
			flag = false;
			writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ----------监听器------

	// 支持多个监听器
	private List<OnRecevieListener> listeners = new ArrayList<OnRecevieListener>();

	/**
	 * 添加监听器
	 * 
	 * @param listener
	 */
	public void addOnRecevieMsgListener(OnRecevieListener listener) {
		listeners.add(listener);
	}

	/**
	 * 删除监听器
	 * 
	 * @param listener
	 */
	public void removeOnRecevieMsgListener(OnRecevieListener listener) {
		listeners.remove(listener);
	}

	private boolean flag = true;

	@Override
	public void run() {
		super.run();
		// 等待 数据
		while (flag) {
			try {
				byte[] buff = new byte[12293];
				while (reader.read(buff) != -1) {
					for (int i = 0; i < listeners.size(); i++) {
						listeners.get(i).onReceive(buff);
					}
				}
			} catch (EOFException e) {
				e.printStackTrace();

				if (who != null) {
					IMConnManager.remove(who);
				}
				disconnect();
			} catch (IOException e) {
				e.printStackTrace();

				if (who != null) {
					IMConnManager.remove(who);
				}
				disconnect();
			}
		}

	}
}
