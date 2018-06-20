package com.cheng.listener;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

import com.cheng.core.IMConnManager;
import com.cheng.core.IMConnection;
import com.cheng.dao.RelationshipDao;
import com.cheng.dao.UserDao;
import com.cheng.domain.MsgType;
import com.cheng.domain.Relationship;
import com.cheng.domain.Request;
import com.cheng.domain.RequestType;
import com.cheng.domain.User;
import com.cheng.utils.Utils;

public class RequestListener extends MessageSender<Request> implements OnRecevieListener {
	private IMConnection conn = null;

	public RequestListener(IMConnection conn) {
		super();
		this.conn = conn;
	}

	public void onReceive(byte[] data) {
		if (checkByte(data)) {
			if (data[3] == MsgType.MSG_TYPE_REQUEST) {
				Request request = getByByte(data, Request.class);
				System.err.println("通用请求" + request.fromUser + "-->>" + request.toUser);

				if (request.type == RequestType.REQUEST_P2P_VOICE_CONNECT) {
					// 语音连接请求,直接发给对方

					// 查看对方是否在线
					if (IMConnManager.conns.containsKey(request.toUser)) {
						try {
							toClient(data, IMConnManager.conns.get(request.toUser));
						} catch (IOException e) {
							try {
								toClient(
										toProtocal(MsgType.MSG_TYPE_FRIEND_NOT_INLINE, "向好友" + request.toUser + "请求失败！"),
										conn);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							e.printStackTrace();
						}
					} else {
						try {
							toClient(toProtocal(MsgType.MSG_TYPE_FRIEND_NOT_INLINE, "您的好友" + request.toUser + "不在线"), conn);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					return;
				} else if (request.type == RequestType.REQUEST_P2P_VOICE_CONNECT_NOT_ACCEPT) {
					// 不同意语音，直接发给对方
					try {
						toClient(data, IMConnManager.conns.get(request.toUser));
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				} else if (request.type == RequestType.REQUEST_P2P_VOICE_CONNECT_ACCEPT) {
					// 对方同意语音连接
					request.type = RequestType.REQUEST_P2P_VOICE_START;
					byte[] newData = toProtocal(MsgType.MSG_TYPE_REQUEST, request);
					// 设置双方的通信对象
					User to = new User();
					to.setId(new BigInteger(request.toUser + ""));
					User fo = new User();
					fo.setId(new BigInteger(request.fromUser + ""));
					try {
					IMConnManager.conns.get(request.toUser).to = fo;
					IMConnManager.conns.get(request.fromUser).to = to;

					// 通知双方客户端可以开始语音
					
						toClient(newData, IMConnManager.conns.get(request.toUser));
					} catch (Exception e) {
						
						try {
							request.type = RequestType.REQUEST_P2P_VOICE_STOP;
							toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request),
									IMConnManager.conns.get(request.fromUser));
							toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request),
									IMConnManager.conns.get(request.toUser));
							
							IMConnManager.conns.get(request.toUser).to = null;
						IMConnManager.conns.get(request.fromUser).to = null;
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
						return;

					}
					try {
						toClient(newData, IMConnManager.conns.get(request.fromUser));
					} catch (IOException e) {

						try {
							request.type = RequestType.REQUEST_P2P_VOICE_STOP;
							toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request),
									IMConnManager.conns.get(request.toUser));

							IMConnManager.conns.get(request.toUser).to = null;
							IMConnManager.conns.get(request.fromUser).to = null;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						e.printStackTrace();
						return;
					}
					return;
				} else if (request.type == RequestType.REQUEST_P2P_VOICE_STOP) {
					// 停止语音通信
					// 设置双方的通信对象
					try {
						toClient(data, IMConnManager.conns.get(request.toUser));

						IMConnManager.conns.get(request.toUser).to = null;
						IMConnManager.conns.get(request.fromUser).to = null;

					} catch (IOException e) {

						e.printStackTrace();
					}
					return;
				} else if (request.type == RequestType.REQUEST_ADD_BUDDTY) {
					// 添加好友请求
					String id = request.content;
					if (id == null) {
						// 无效请求，返回
						request.type = RequestType.REQUEST_ADD_BUDDTY_FAILE;
						request.content = "无效请求！ ";
						try {
							toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request), conn);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;
					} else {
						UserDao dao = new UserDao();
						User user = new User();

						if (Utils.isInteger(id)) {
							// 查询值不是邮箱
							user.setId(new BigInteger(id));
						} else {
							user.setEmail(id);
						}
						try {
							User dbUser = dao.find(user);
							if (dbUser == null) {
								// 用户未注册
								request.type = RequestType.REQUEST_ADD_BUDDTY_FAILE;
								request.content = "该用户未注册！ ";
								try {
									toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request), conn);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								return;
							} else {
								// 插入数据库
								RelationshipDao relationshipDao = new RelationshipDao();
								long toId = dbUser.getId().longValue();
								Relationship relationship = new Relationship();
								relationship.setUser_a_id(new BigInteger(request.fromUser + ""));
								relationship.setUser_b_id(dbUser.getId());
								relationship.setRequest(1);
								relationship = relationshipDao.insert(relationship);

								if (relationship != null && relationship.getId() != null) {
									// 申请成功
									if (IMConnManager.conns.containsKey(toId)) {
										// 对方在线，发送给对方
										try {
											toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request),
													IMConnManager.conns.get(toId));
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									request.type = RequestType.REQUEST_ADD_BUDDTY_SUCCESS;
									request.content = "请等待对方接受！ ";
									try {
										toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request), conn);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								} else {
									request.type = RequestType.REQUEST_ADD_BUDDTY_FAILE;
									request.content = "已发送请求！ ";
									try {
										toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request), conn);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								return;
							}
						} catch (SQLException e) {
							request.type = RequestType.REQUEST_ADD_BUDDTY_FAILE;
							request.content = "该用户未注册！ ";
							try {
								toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request), conn);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							e.printStackTrace();
						}
					}

				} else {
					try {
						toClient(data, IMConnManager.conns.get(request.toUser));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}

	}

}
