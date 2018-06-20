package com.cheng.domain;

public class RequestType {

	/**
	 * 发送语音连接请求
	 */
	public static final int REQUEST_P2P_VOICE_CONNECT = 11;
	/**
	 * 接受语音连接
	 */
	public static final byte REQUEST_P2P_VOICE_CONNECT_ACCEPT = 12;
	/**
	 * 拒绝语音连接
	 */
	public static final byte REQUEST_P2P_VOICE_CONNECT_NOT_ACCEPT = 13;
	/**
	 * 开始语音通话
	 */
	public static final byte REQUEST_P2P_VOICE_START = 14;
	/**
	 * 结束语音通话
	 */
	public static final byte REQUEST_P2P_VOICE_STOP = 15;

	/**
	 * 发送添加好友请求
	 */
	public static final int REQUEST_ADD_BUDDTY = 21;
	/**
	 * 发送失败
	 */
	public static final int REQUEST_ADD_BUDDTY_FAILE = 24;
	/**
	 * 发送成功
	 */
	public static final int REQUEST_ADD_BUDDTY_SUCCESS = 25;
	/**
	 * 接受添加好友请求
	 */
	public static final byte REQUEST_ADD_BUDDTY_ACCEPT = 22;
	/**
	 * 拒绝添加好友请求
	 */
	public static final byte REQUEST_ADD_BUDDTY_NOT_ACCEPT = 23;
	
	/**
	 * 文件传输请求
	 */
	public static final byte REQUEST_FILE = 31;

}
