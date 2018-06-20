package com.cheng.listener;

import java.io.IOException;

import com.cheng.core.IMConnManager;
import com.cheng.core.IMConnection;
import com.cheng.domain.MsgType;

public class ChatP2PVoiceListener extends MessageSender<Object> implements OnRecevieListener {
	private IMConnection conn = null;

	public ChatP2PVoiceListener(IMConnection conn) {
		super();
		this.conn = conn;
	}

	public void onReceive(byte[] data) {
		if (checkByte(data)) {
			if (data[3] == MsgType.MSG_TYPE_CHAT_P2P_VOICE||data[3] ==MsgType.MSG_TYPE_CHAT_P2P_VOICE_CODE) {
				 System.out.print(data[0]+""+data[1]+""+data[2]+""+data[3]+""+data[4]+"");
				 System.out.println();
				try {
					if (conn.to != null&&IMConnManager.conns.containsKey(conn.to.getId().longValue())) {
						System.err.println(conn.who.getId().longValue()+">>>语音发送>>>"+conn.to.getId().longValue());
						System.out.println(IMConnManager.conns.get(conn.to.getId().longValue()).who.getId());
						
						System.out.println(data.length+"******");
						toClient(data, IMConnManager.conns.get(conn.to.getId().longValue()));
					} else {
						//toClient(toProtocal(MsgType.MSG_TYPE_CHAT_P2P_VOICE_FAILE, "连接中断1！"), conn);
					}

				} catch (IOException e) {
					try {
						toClient(toProtocal(MsgType.MSG_TYPE_CHAT_P2P_VOICE_FAILE, "连接中断2！"), conn);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					e.printStackTrace();
				}

			}

		}
	}

}
