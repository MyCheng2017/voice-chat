package com.cheng.domain;

import java.math.BigInteger;
import java.sql.Date;

public class Relationship {

	private BigInteger id;
	private BigInteger user_a_id;
	private BigInteger user_b_id;

	private int request;
	private Date request_time;

	private int result;
	private Date result_time;

	private int relationship;
	private int permission_b;
	private int permission_a;

	private Date gmt_modified;
	private Date gmt_create;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getUser_a_id() {
		return user_a_id;
	}
	public void setUser_a_id(BigInteger user_a_id) {
		this.user_a_id = user_a_id;
	}
	public BigInteger getUser_b_id() {
		return user_b_id;
	}
	public void setUser_b_id(BigInteger user_b_id) {
		this.user_b_id = user_b_id;
	}
	public int getRequest() {
		return request;
	}
	public void setRequest(int request) {
		this.request = request;
	}
	public Date getRequest_time() {
		return request_time;
	}
	public void setRequest_time(Date request_time) {
		this.request_time = request_time;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public Date getResult_time() {
		return result_time;
	}
	public void setResult_time(Date result_time) {
		this.result_time = result_time;
	}
	public int getRelationship() {
		return relationship;
	}
	public void setRelationship(int relationship) {
		this.relationship = relationship;
	}
	public int getPermission_b() {
		return permission_b;
	}
	public void setPermission_b(int permission_b) {
		this.permission_b = permission_b;
	}
	public int getPermission_a() {
		return permission_a;
	}
	public void setPermission_a(int permission_a) {
		this.permission_a = permission_a;
	}
	public Date getGmt_modified() {
		return gmt_modified;
	}
	public void setGmt_modified(Date gmt_modified) {
		this.gmt_modified = gmt_modified;
	}
	public Date getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}
	
}
