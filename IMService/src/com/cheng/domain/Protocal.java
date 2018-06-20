package com.cheng.domain;

import com.google.gson.Gson;
 



public class Protocal {
	public String toJson() {
		Gson g = new Gson();
		return g.toJson(this);
	}
	public Object fromJson(String json) {
		Gson g = new Gson();
		return g.fromJson(json, this.getClass());
	}
	
}
