package com.cheng.domain;

import java.util.Date;

public class IMMessage extends Protocal {
	public int id = 0;
	public String type;// 类型的数据text
	public long fromUser = 0L;// 发送者 account
	public String fromNick = "";// 昵称
	public String fromAvatar = "1";// 头像
	public long toUser = 0L; // 接收者 account
	public String content = ""; // 消息的内容
	public long createTime; // 发送时间
	public int isRead = 0; // 发送时间
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
