package com.cheng.domain;

import java.util.Date;

public class Request extends Protocal {
	public int type;// ���͵�����text
	public long fromUser = 0L;// ������ account
	public String fromNick = "";// �ǳ�
	public String fromAvatar = "1";// ͷ��
	public long toUser = 0L; // ������ account

	public String content = ""; // ��Ϣ������
	public long createTime; // ����ʱ��

	public Request() {
	}

	public Request(User from, long to, String content, int type) {
		this.fromUser = from.getId().longValue();
		this.fromNick = from.getNickname();
		this.fromAvatar = from.getAvatar();
		this.toUser = to;
		this.content = content;
		this.type = type;
		this.createTime = new Date().getTime();

	}
}
