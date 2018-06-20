package com.cheng.listener;

import com.cheng.core.MessageSender;
import com.cheng.domain.Message;
import com.cheng.domain.MsgType;
import com.cheng.main.App;
import com.google.gson.Gson;

public class RegisterListener extends MessageSender implements OnRecevieListener {
	private ViewHandler handler = App.getAplication();
	Gson gson = new Gson();

	public void onReceive(byte[] data) {
		if (checkByte(data)) {
			if (MsgType.MSG_TYPE_REGISTER_SUCCESS == data[3] || MsgType.MSG_TYPE_REGISTER_FAILE == data[3]) {
				System.err.println("接收到：注册结果信息");
				Message msg = new Message();
				msg.what = data[3];
				msg.msg=new String(data, 5, data[1] * 100 + data[2]);
				handler.handleMessage(msg);
			}
		}

	}

	public void setHandler(ViewHandler handler) {
		this.handler = handler;
	}
}
