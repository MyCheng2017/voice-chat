package com.cheng.core;

import java.io.IOException;

import com.cheng.domain.IMMessage;
import com.cheng.domain.MsgType;
import com.cheng.domain.Request;
import com.cheng.domain.User;

public class Service extends MessageSender {

	/**
	 * ע�����
	 * @param user
	 * @throws IOException
	 */
	public void register(User user) throws IOException {
		super.send(toProtocal(MsgType.MSG_TYPE_REGISTER, user));
	}
	
	/**
	 * ע����֤����
	 * @param user
	 * @throws IOException
	 */
	public void registerConfirm(User user) throws IOException {
		super.send(toProtocal(MsgType.MSG_TYPE_REGISTER_CODE, user));
	}
	/**
	 * ��¼����
	 * @param user
	 * @throws IOException
	 */
	public void login(User user) throws IOException {
		super.send(toProtocal(MsgType.MSG_TYPE_LOGIN, user));
	}
	/**
	 * ����������Ϣ
	 * @param message
	 * @throws IOException
	 */
	public void send(IMMessage message) throws IOException {
		super.send(toProtocal(MsgType.MSG_TYPE_CHAT_P2P_TEXT, message));
	}
	/**
	 * ����������Ϣ
	 * @param data
	 * @throws IOException
	 */
	public void send(byte[] data) throws IOException {
		
		super.send(data);
	}
	
	/**
	 * ��������
	 * @param message
	 * @throws IOException
	 */
	public void send(Request request) throws IOException {
		super.send(toProtocal(MsgType.MSG_TYPE_REQUEST, request));
	}
}
