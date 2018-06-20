package com.cheng.listener;

import java.math.BigInteger;

import com.cheng.core.IMConnManager;
import com.cheng.domain.MsgType;
import com.cheng.domain.User;

public class LoginOutListener extends MessageSender<Object> implements OnRecevieListener {

	public void onReceive(byte[] data) {
		if (checkByte(data)) {

			if (MsgType.MSG_TYPE_LOGIN_OUT == data[3]) {
				String id = new String(data, 5, data[1] * 100 + data[2]);
				User user = new User();
				System.err.println("×¢ÏúÇëÇó" + id);
				user.setId(new BigInteger(id));
				IMConnManager.remove(user);
			}
		}

	}

}