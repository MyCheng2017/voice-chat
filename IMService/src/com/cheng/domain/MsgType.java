package com.cheng.domain;

public class MsgType {

	/**
	 * 登录
	 */
	public static final byte MSG_TYPE_LOGIN = 11;
	/**
	 * 登录成功
	 */
	public static final byte MSG_TYPE_LOGIN_SUCCESS = 12;
	/**
	 * 登录失败
	 */
	public static final byte MSG_TYPE_LOGIN_FAILE = 13;

	public static final byte MSG_TYPE_LOGIN_SUCCESS_DATA_BUDDY = 14;
	public static final byte MSG_TYPE_LOGIN_SUCCESS_DATA_MESSAGE = 15;
	/**
	 * 注册
	 */
	public static final byte MSG_TYPE_REGISTER = 21;
	/**
	 * 注册成功
	 */
	public static final byte MSG_TYPE_REGISTER_SUCCESS = 22;
	/**
	 * 注册失败
	 */
	public static final byte MSG_TYPE_REGISTER_FAILE = 23;

	/**
	 * 注销
	 */
	public static final byte MSG_TYPE_LOGIN_OUT = 31;
	/**
	 * 注销成功
	 */
	public static final byte MSG_TYPE_LOGIN_OUT_SUCCESS = 32;
	/**
	 * 注销失败
	 */
	public static final byte MSG_TYPE_LOGIN_OUT_FAILE = 33;

	/**
	 * 注销
	 */
	public static final byte MSG_TYPE_REGISTER_CODE = 41;
	/**
	 * 注销成功
	 */
	public static final byte MSG_TYPE_REGISTER_CODE_SUCCESS = 42;
	/**
	 * 注销失败
	 */
	public static final byte MSG_TYPE_REGISTER_CODE_FAILE = 43;

	/**
	 * 注销
	 */
	public static final byte MSG_TYPE_REGISTER_CODE_RESEND = 44;
	/**
	 * 注销成功
	 */
	public static final byte MSG_TYPE_REGISTER_CODE_RESEND_SUCCESS = 45;
	/**
	 * 注销失败
	 */
	public static final byte MSG_TYPE_REGISTER_CODE_RESEND_FAILE = 46;

	public static final byte MSG_TYPE_REQUEST = 51;

	/**
	 * 文字聊天
	 */
	public static final byte MSG_TYPE_CHAT_P2P_TEXT = 61;
	public static final byte MSG_TYPE_CHAT_P2P_TEXT_UNREAD = 62;
	public static final byte MSG_TYPE_CHAT_P2P_TEXT_READ = 63;
	/**
	 * 语音聊天
	 */
	public static final byte MSG_TYPE_CHAT_P2P_VOICE = 71;
	public static final byte MSG_TYPE_CHAT_P2P_VOICE_FAILE = 72;
	public static final byte MSG_TYPE_CHAT_P2P_VOICE_CODE = 73;
	/**
	 * 
	 */
	public static final byte MSG_TYPE_CHAT_ROOM_TEXT = 81;// 群聊

	public static final byte MSG_TYPE_SUCCESS = 127;// 成功

	public static final byte MSG_TYPE_FAILURE = -127;// 失败
	/**
	 * 好友上线
	 */
	public static final byte MSG_TYPE_FRIEND_LOGIN = 91;
	/**
	 * 好友不在线
	 */
	public static final byte MSG_TYPE_FRIEND_NOT_INLINE = 92;
	
	/**
	 * 文件传输
	 */
	public static final byte MSG_TYPE_FILE = 101;
	/**
	 * 修改信息
	 */
	public static final byte MSG_TYPE_MODIFY_INFO = 110;
	
	/**
	 * 修改密码
	 */
	public static final byte MSG_TYPE_MODIFY_PASSWORD = 111;

}
