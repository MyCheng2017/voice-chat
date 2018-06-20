package com.cheng.core;

import java.io.IOException;

import com.cheng.domain.IMMessage;
import com.cheng.domain.MsgType;
import com.cheng.domain.Request;
import com.cheng.domain.User;

public class Service extends MessageSender {

	/**
	 * 注册服务
	 * @param user
	 * @throws IOException
	 */
	public void register(User user) throws IOException {
		super.send(toProtocal(MsgType.MSG_TYPE_REGISTER, user));
	}
	
	/**
	 * 注册验证服务
	 * @param user
	 * @throws IOException
	 */
	public void registerConfirm(User user) throws IOException {
		super.send(toProtocal(MsgType.MSG_TYPE_REGISTER_CODE, user));
	}
	/**
	 * 登录服务
	 * @param user
	 * @throws IOException
	 */
	public void login(User user) throws IOException {
		super.send(toProtocal(MsgType.MSG_TYPE_LOGIN, user));
	}
	/**
	 * 发送文字消息
	 * @param message
	 * @throws IOException
	 */
	public void send(IMMessage message) throws IOException {
		super.send(toProtocal(MsgType.MSG_TYPE_CHAT_P2P_TEXT, message));
	}
	/**
	 * 发送语音消息
	 * @param data
	 * @throws IOException
	 */
	public void send(byte[] data) throws IOException {
		
		super.send(data);
	}
	
	/**
	 * 发送请求
	 * @param message
	 * @throws IOException
	 */
	public void send(Request request) throws IOException {
		super.send(toProtocal(MsgType.MSG_TYPE_REQUEST, request));
	}
}
