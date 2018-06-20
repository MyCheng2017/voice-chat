package com.cheng.domain;

public class Message {
	public int what = 0;
	public Object obj = null;
	public String msg = null;

	public Message() {
	}

	public Message(int what, Object obj, String msg) {
		this.what = what;
		this.obj = obj;
		this.msg = msg;
	}
}
