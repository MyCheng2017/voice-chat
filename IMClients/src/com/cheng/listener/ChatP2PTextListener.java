package com.cheng.listener;

import java.io.IOException;

import com.cheng.core.MessageSender;
import com.cheng.domain.IMMessage;
import com.cheng.domain.Message;
import com.cheng.domain.MsgType;
import com.cheng.main.App;
import com.cheng.utils.ChatLog;
import com.google.gson.Gson;

public class ChatP2PTextListener extends MessageSender implements OnRecevieListener {
	private  ViewHandler handler   = App.getAplication();

	public void onReceive(byte[] data) {
		if (checkByte(data)) {
			if (data[3] == MsgType.MSG_TYPE_CHAT_P2P_TEXT) {
				System.err.println("接收到：文字聊天");
				String json = new String(data, 5,data[1]*100+data[2]);
				Gson gson = new Gson();
				IMMessage message = gson.fromJson(json, IMMessage.class);
				// 插入聊天记录
				try {
					Message msg = new Message();
					message.content=message.content.replaceAll("text-align: right;", "text-align: left;");
					System.out.println(message.content);
					 ChatLog.insertFromUser(message);
					 msg.what= MsgType.MSG_TYPE_CHAT_P2P_TEXT;
					msg.obj =message;
					handler.handleMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if (data[3] == MsgType.MSG_TYPE_CHAT_P2P_TEXT_READ||data[3] == MsgType.MSG_TYPE_CHAT_P2P_TEXT_UNREAD) {
				Message msg = new Message();
				msg.what=data[3];
				String json = new String(data, 5,data[1]*100+data[2]);
				Gson gson = new Gson();
				IMMessage message = gson.fromJson(json, IMMessage.class);
				msg.obj =message;
				handler.handleMessage(msg);
			} 

		}

	}

	public  void setHandler(ViewHandler handler) {
		this.handler = handler;
	}
}
