package com.cheng.domain;

public class MsgType {

	/**
	 * ��¼
	 */
	public static final byte MSG_TYPE_LOGIN = 11;
	/**
	 * ��¼�ɹ�
	 */
	public static final byte MSG_TYPE_LOGIN_SUCCESS = 12;
	/**
	 * ��¼ʧ��
	 */
	public static final byte MSG_TYPE_LOGIN_FAILE = 13;

	public static final byte MSG_TYPE_LOGIN_SUCCESS_DATA_BUDDY = 14;
	public static final byte MSG_TYPE_LOGIN_SUCCESS_DATA_MESSAGE = 15;
	/**
	 * ע��
	 */
	public static final byte MSG_TYPE_REGISTER = 21;
	/**
	 * ע��ɹ�
	 */
	public static final byte MSG_TYPE_REGISTER_SUCCESS = 22;
	/**
	 * ע��ʧ��
	 */
	public static final byte MSG_TYPE_REGISTER_FAILE = 23;

	/**
	 * ע��
	 */
	public static final byte MSG_TYPE_LOGIN_OUT = 31;
	/**
	 * ע���ɹ�
	 */
	public static final byte MSG_TYPE_LOGIN_OUT_SUCCESS = 32;
	/**
	 * ע��ʧ��
	 */
	public static final byte MSG_TYPE_LOGIN_OUT_FAILE = 33;

	/**
	 * ע��
	 */
	public static final byte MSG_TYPE_REGISTER_CODE = 41;
	/**
	 * ע���ɹ�
	 */
	public static final byte MSG_TYPE_REGISTER_CODE_SUCCESS = 42;
	/**
	 * ע��ʧ��
	 */
	public static final byte MSG_TYPE_REGISTER_CODE_FAILE = 43;

	/**
	 * ע��
	 */
	public static final byte MSG_TYPE_REGISTER_CODE_RESEND = 44;
	/**
	 * ע���ɹ�
	 */
	public static final byte MSG_TYPE_REGISTER_CODE_RESEND_SUCCESS = 45;
	/**
	 * ע��ʧ��
	 */
	public static final byte MSG_TYPE_REGISTER_CODE_RESEND_FAILE = 46;

	public static final byte MSG_TYPE_REQUEST = 51;

	/**
	 * ��������
	 */
	public static final byte MSG_TYPE_CHAT_P2P_TEXT = 61;
	public static final byte MSG_TYPE_CHAT_P2P_TEXT_UNREAD = 62;
	public static final byte MSG_TYPE_CHAT_P2P_TEXT_READ = 63;
	/**
	 * ��������
	 */
	public static final byte MSG_TYPE_CHAT_P2P_VOICE = 71;
	public static final byte MSG_TYPE_CHAT_P2P_VOICE_FAILE = 72;
	public static final byte MSG_TYPE_CHAT_P2P_VOICE_CODE = 73;
	/**
	 * 
	 */
	public static final byte MSG_TYPE_CHAT_ROOM_TEXT = 81;// Ⱥ��

	public static final byte MSG_TYPE_SUCCESS = 127;// �ɹ�

	public static final byte MSG_TYPE_FAILURE = -127;// ʧ��
	/**
	 * ��������
	 */
	public static final byte MSG_TYPE_FRIEND_LOGIN = 91;
	/**
	 * ���Ѳ�����
	 */
	public static final byte MSG_TYPE_FRIEND_NOT_INLINE = 92;
	
	/**
	 * �ļ�����
	 */
	public static final byte MSG_TYPE_FILE = 101;
	/**
	 * �޸���Ϣ
	 */
	public static final byte MSG_TYPE_MODIFY_INFO = 110;
	
	/**
	 * �޸�����
	 */
	public static final byte MSG_TYPE_MODIFY_PASSWORD = 111;

}
