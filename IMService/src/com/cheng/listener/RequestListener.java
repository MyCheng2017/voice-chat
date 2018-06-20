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
				System.err.println("ͨ������" + request.fromUser + "-->>" + request.toUser);

				if (request.type == RequestType.REQUEST_P2P_VOICE_CONNECT) {
					// ������������,ֱ�ӷ����Է�

					// �鿴�Է��Ƿ�����
					if (IMConnManager.conns.containsKey(request.toUser)) {
						try {
							toClient(data, IMConnManager.conns.get(request.toUser));
						} catch (IOException e) {
							try {
								toClient(
										toProtocal(MsgType.MSG_TYPE_FRIEND_NOT_INLINE, "�����" + request.toUser + "����ʧ�ܣ�"),
										conn);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							e.printStackTrace();
						}
					} else {
						try {
							toClient(toProtocal(MsgType.MSG_TYPE_FRIEND_NOT_INLINE, "���ĺ���" + request.toUser + "������"), conn);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					return;
				} else if (request.type == RequestType.REQUEST_P2P_VOICE_CONNECT_NOT_ACCEPT) {
					// ��ͬ��������ֱ�ӷ����Է�
					try {
						toClient(data, IMConnManager.conns.get(request.toUser));
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				} else if (request.type == RequestType.REQUEST_P2P_VOICE_CONNECT_ACCEPT) {
					// �Է�ͬ����������
					request.type = RequestType.REQUEST_P2P_VOICE_START;
					byte[] newData = toProtocal(MsgType.MSG_TYPE_REQUEST, request);
					// ����˫����ͨ�Ŷ���
					User to = new User();
					to.setId(new BigInteger(request.toUser + ""));
					User fo = new User();
					fo.setId(new BigInteger(request.fromUser + ""));
					try {
					IMConnManager.conns.get(request.toUser).to = fo;
					IMConnManager.conns.get(request.fromUser).to = to;

					// ֪ͨ˫���ͻ��˿��Կ�ʼ����
					
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
					// ֹͣ����ͨ��
					// ����˫����ͨ�Ŷ���
					try {
						toClient(data, IMConnManager.conns.get(request.toUser));

						IMConnManager.conns.get(request.toUser).to = null;
						IMConnManager.conns.get(request.fromUser).to = null;

					} catch (IOException e) {

						e.printStackTrace();
					}
					return;
				} else if (request.type == RequestType.REQUEST_ADD_BUDDTY) {
					// ��Ӻ�������
					String id = request.content;
					if (id == null) {
						// ��Ч���󣬷���
						request.type = RequestType.REQUEST_ADD_BUDDTY_FAILE;
						request.content = "��Ч���� ";
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
							// ��ѯֵ��������
							user.setId(new BigInteger(id));
						} else {
							user.setEmail(id);
						}
						try {
							User dbUser = dao.find(user);
							if (dbUser == null) {
								// �û�δע��
								request.type = RequestType.REQUEST_ADD_BUDDTY_FAILE;
								request.content = "���û�δע�ᣡ ";
								try {
									toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request), conn);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								return;
							} else {
								// �������ݿ�
								RelationshipDao relationshipDao = new RelationshipDao();
								long toId = dbUser.getId().longValue();
								Relationship relationship = new Relationship();
								relationship.setUser_a_id(new BigInteger(request.fromUser + ""));
								relationship.setUser_b_id(dbUser.getId());
								relationship.setRequest(1);
								relationship = relationshipDao.insert(relationship);

								if (relationship != null && relationship.getId() != null) {
									// ����ɹ�
									if (IMConnManager.conns.containsKey(toId)) {
										// �Է����ߣ����͸��Է�
										try {
											toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request),
													IMConnManager.conns.get(toId));
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									request.type = RequestType.REQUEST_ADD_BUDDTY_SUCCESS;
									request.content = "��ȴ��Է����ܣ� ";
									try {
										toClient(toProtocal(MsgType.MSG_TYPE_REQUEST, request), conn);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								} else {
									request.type = RequestType.REQUEST_ADD_BUDDTY_FAILE;
									request.content = "�ѷ������� ";
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
							request.content = "���û�δע�ᣡ ";
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
