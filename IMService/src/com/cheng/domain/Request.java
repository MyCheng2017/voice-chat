package com.cheng.domain;

import java.util.Date;

public class Request extends Protocal {
	public int type;// 类型的数据text
	public long fromUser = 0L;// 发送者 account
	public String fromNick = "";// 昵称
	public String fromAvatar = "1";// 头像
	public long toUser = 0L; // 接收者 account

	public String content = ""; // 消息的内容
	public long createTime; // 发送时间

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
