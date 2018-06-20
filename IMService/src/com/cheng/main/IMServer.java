package com.cheng.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.cheng.core.IMConnection;
import com.cheng.listener.LoginListener;
import com.cheng.listener.RegisterConfirmListener;
import com.cheng.listener.RegisterListener;
import com.cheng.utils.LogUtils;

public class IMServer {
	
	public static void main(String[] args) {
		try {
			// ����һ���߳� �������ͻ��˵�����
			@SuppressWarnings("resource")
			final ServerSocket server =new ServerSocket(8729);  
			System.out.println("����������:" + new Date().toString());
			new Thread() {//
				
				@SuppressWarnings("unused")
				public void run() {
					while (true) {
						IMConnection conn = null;
						try {
							Socket client = server.accept();
							System.out.println("�пͻ��˽���:" + client);
							LogUtils.log("IMServer", "�пͻ��˽���:" + client);
							// ����ͻ������ӳɹ�������һ���߳�
							conn = new IMConnection(client);
							//δ��¼�û�
							//�ṩע�ᡢ��¼��ע����֤�����·�����֤�����
							conn.addOnRecevieMsgListener(new LoginListener(conn));
							conn.addOnRecevieMsgListener(new RegisterListener(conn));
							conn.addOnRecevieMsgListener(new RegisterConfirmListener(conn));
							 
						//conn.writer.write("22".getBytes());
							// ���߳��ڵȴ��û�����
							conn.connect();

						} catch (IOException e) {
							e.printStackTrace();
							if (conn != null)
								conn.disconnect();
						}
					}
				};
			}.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
