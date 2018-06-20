package com.cheng.listener;

import com.cheng.core.MessageSender;
import com.cheng.domain.Message;
import com.cheng.domain.MsgType;
import com.cheng.domain.Request;
import com.cheng.main.App;
import com.google.gson.Gson;

public class RequestListener extends MessageSender implements OnRecevieListener {
	private static ViewHandler handler = App.getAplication();

	public void onReceive(byte[] data) {
		if (checkByte(data)) {
			if (data[3] == MsgType.MSG_TYPE_REQUEST) {
				System.err.println("接收到：请求信息");
				String json = new String(data, 5, data[1] * 100 + data[2]);
				Gson gson = new Gson();
				Request request = gson.fromJson(json, Request.class);
				Message msg = new Message();
				msg.what = MsgType.MSG_TYPE_REQUEST;
				msg.obj = request;
				handler.handleMessage(msg);

			} else if (data[3] == MsgType.MSG_TYPE_CHAT_P2P_VOICE_FAILE) {
				Gson gson = new Gson();
				Message msg = new Message();
				msg.what = MsgType.MSG_TYPE_CHAT_P2P_VOICE_FAILE;

				msg.msg = gson.fromJson(new String(data, 5, data[1] * 100 + data[2]), String.class);
				handler.handleMessage(msg);
			} else if (data[3] == MsgType.MSG_TYPE_FRIEND_NOT_INLINE) {
				Gson gson = new Gson();
				Message msg = new Message();
				msg.what = MsgType.MSG_TYPE_FRIEND_NOT_INLINE;

				msg.msg = gson.fromJson(new String(data, 5, data[1] * 100 + data[2]), String.class);
				handler.handleMessage(msg);
			}

		}

	}

	public static void setHandler(ViewHandler handler) {
		RequestListener.handler = handler;
	}
}
