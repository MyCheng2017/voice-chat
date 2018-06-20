package com.cheng.listener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cheng.core.IMConnManager;
import com.cheng.core.IMConnection;
import com.cheng.dao.MessageDao;
import com.cheng.dao.RelationshipDao;
import com.cheng.dao.UserDao;
import com.cheng.domain.IMMessage;
import com.cheng.domain.MsgType;
import com.cheng.domain.Relationship;
import com.cheng.domain.User;
import com.cheng.utils.MD5;

public class LoginListener extends MessageSender<User> implements OnRecevieListener {
	private IMConnection conn = null;

	public LoginListener(IMConnection conn) {
		super();
		this.conn = conn;
	}

	public void onReceive(byte[] b) {

		if (checkByte(b)) {

			if (b[3] == MsgType.MSG_TYPE_LOGIN) {

				User user = getByByte(b, User.class);
				if (user == null) {
					return;
				}
				System.err.println("登录请求" + user.getId());
				user.setPassword(MD5.md5(user.getPassword()));
				UserDao userDao = new UserDao();

				User result = null;
				try {
					result = userDao.login(user);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (result != null) {
					if (result.getIs_actived() != 1) {
						try {
							conn.writer.write(toProtocal(MsgType.MSG_TYPE_LOGIN_FAILE, "该账号未激活！"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						return;
					}
					result.setIs_actived(0);
					result.setPassword("");
					// 登录成功，获取好友列表和在线列表
					RelationshipDao relationshipDao = new RelationshipDao();
					List<Relationship> relationships;
					List<User> users = new ArrayList<User>();
					try {
						relationships = relationshipDao.select(result);
						users = userDao.select(user, relationships);
					} catch (SQLException e) {
						e.printStackTrace();
					}

					MessageDao messageDao = new MessageDao();
					List<IMMessage> messages = null;
					try {
						messages = messageDao.select(result);
					} catch (SQLException e) {
						e.printStackTrace();
					}

					// 添加其他监听
					conn.addOnRecevieMsgListener(new ChatP2PTextListener());
					conn.addOnRecevieMsgListener(new ChatP2PVoiceListener(conn));
					conn.addOnRecevieMsgListener(new ChatRoomListener());
					conn.addOnRecevieMsgListener(new LoginOutListener());
					conn.addOnRecevieMsgListener(new RequestListener(conn));
					// 通知客户端登录成功，并返回好友列表

					// 创建带身份的连接对象
					conn.who = result;
					IMConnManager.put(result, conn);
					// 通知好友我上线了
					user.setPassword("");

					try {
						conn.writer.write(toProtocal(MsgType.MSG_TYPE_LOGIN_SUCCESS, result));
						conn.writer.write(listToProtocal(MsgType.MSG_TYPE_LOGIN_SUCCESS_DATA_BUDDY, users));
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (messages != null) {

						
						try {
							Thread.sleep(100);
							toClient(listToProtocal(MsgType.MSG_TYPE_LOGIN_SUCCESS_DATA_MESSAGE, messages), conn);
							
							messageDao.delete(result);
						} catch (IOException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}

					user.setIs_actived(0);
				/*	try {
						toEveryClient(users, toProtocal(MsgType.MSG_TYPE_FRIEND_LOGIN, user));
					} catch (IOException e) {
						e.printStackTrace();
					}*/
				} else {
					// 登录失败
					try {
						conn.writer.write(toProtocal(MsgType.MSG_TYPE_LOGIN_FAILE, "用户名或密码错误！"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}

			}
		}

	}

}