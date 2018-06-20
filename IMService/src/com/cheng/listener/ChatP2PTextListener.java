package com.cheng.listener;

import java.io.IOException;
import java.sql.SQLException;

import com.cheng.core.IMConnManager;
import com.cheng.dao.MessageDao;
import com.cheng.domain.IMMessage;
import com.cheng.domain.MsgType;

public class ChatP2PTextListener extends MessageSender<IMMessage> implements OnRecevieListener {
MessageDao messageDao=new MessageDao();
	public void onReceive(byte[] data) {
		if (checkByte(data)) {
			if (data[3] == MsgType.MSG_TYPE_CHAT_P2P_TEXT) {

				IMMessage message = getByByte(data, IMMessage.class);
				System.err.println("文字聊天请求" + message.fromUser + "-->>" + message.toUser);

				if (IMConnManager.conns.containsKey(message.toUser)) {
					// 对方在线
					try {
						toClient(data, IMConnManager.conns.get(message.toUser));
						System.err.println("发送完毕文字聊天" + message.fromUser + "-->>" + message.toUser);
						message.isRead = 1;
						toClient(toProtocal(MsgType.MSG_TYPE_CHAT_P2P_TEXT_READ, message),
								IMConnManager.conns.get(message.fromUser));
					} catch (IOException e) {
						try {
							message.isRead = 0;
							toClient(toProtocal(MsgType.MSG_TYPE_CHAT_P2P_TEXT_UNREAD, message),
									IMConnManager.conns.get(message.fromUser));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
				} else {

					try {
						messageDao.insert(message);
						message.isRead = 0;
						toClient(toProtocal(MsgType.MSG_TYPE_CHAT_P2P_TEXT_UNREAD, message),
								IMConnManager.conns.get(message.fromUser));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		}

	}

}
