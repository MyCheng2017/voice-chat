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
			// 创建一个线程 等其他客户端的连接
			@SuppressWarnings("resource")
			final ServerSocket server =new ServerSocket(8729);  
			System.out.println("服务器启动:" + new Date().toString());
			new Thread() {//
				
				@SuppressWarnings("unused")
				public void run() {
					while (true) {
						IMConnection conn = null;
						try {
							Socket client = server.accept();
							System.out.println("有客户端接入:" + client);
							LogUtils.log("IMServer", "有客户端接入:" + client);
							// 如果客户端连接成功分配置一个线程
							conn = new IMConnection(client);
							//未登录用户
							//提供注册、登录、注册验证、重新发送验证码服务
							conn.addOnRecevieMsgListener(new LoginListener(conn));
							conn.addOnRecevieMsgListener(new RegisterListener(conn));
							conn.addOnRecevieMsgListener(new RegisterConfirmListener(conn));
							 
						//conn.writer.write("22".getBytes());
							// 该线程内等待用户数据
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
