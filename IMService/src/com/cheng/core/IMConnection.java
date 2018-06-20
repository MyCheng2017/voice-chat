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
 * ��socket��װ�ɴ����û���Ϣ���࣬������������Ϣ����
 * 
 * @author ChengAcer
 *
 */
public class IMConnection extends Thread {

	private Socket socket = null;
	/**
	 * ���������
	 */
	public DataOutputStream writer = null;
	/**
	 * ����������
	 */
	public DataInputStream reader = null;
	/**
	 * ˭������
	 */
	public User who = null;
	/**
	 * ��ǰͨ�Ŷ���
	 */
	public User to = null;
	/**
	 * ���ӵ�ip
	 */
	public String ip;
	/**
	 * ���Ӷ˿�
	 */
	public int port;

	/**
	 * �Ӵ�������socket��ȡ�������������
	 * 
	 * @param scoket
	 *            �û����ӵ�socket
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
	 * ͨ��ip�Ͷ˿ڴ���socket������ȡ���������
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
	 * ���socket�Ͽ����������Ӳ���ʼ������Ϣ������ֱ�Ӽ�����Ϣ
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
	 * �Ͽ�����
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

	// ----------������------

	// ֧�ֶ��������
	private List<OnRecevieListener> listeners = new ArrayList<OnRecevieListener>();

	/**
	 * ��Ӽ�����
	 * 
	 * @param listener
	 */
	public void addOnRecevieMsgListener(OnRecevieListener listener) {
		listeners.add(listener);
	}

	/**
	 * ɾ��������
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
		// �ȴ� ����
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
