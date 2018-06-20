package com.cheng.domain;

import java.util.Date;

public class IMMessage extends Protocal {
	public int id = 0;
	public String type;// ���͵�����text
	public long fromUser = 0L;// ������ account
	public String fromNick = "";// �ǳ�
	public String fromAvatar = "1";// ͷ��
	public long toUser = 0L; // ������ account
	public String content = ""; // ��Ϣ������
	public long createTime; // ����ʱ��
	public int isRead = 0; // ����ʱ��
	public static String TYPE_TEXT = "text";
	public static String TYPE_VOICE = "voice";
	public static String TYPE_IMG = "img";

	public IMMessage() {
	}

	public IMMessage(User from, long to, String content, String type) {
		this.fromUser = from.getId().longValue();
		this.fromNick = from.getNickname();
		this.fromAvatar = from.getAvatar();
		this.toUser = to;
		this.content = content;
		this.type = type;
		this.createTime = new Date().getTime();
	}
}
