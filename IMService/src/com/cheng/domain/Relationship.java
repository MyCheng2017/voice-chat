package com.cheng.domain;

import java.math.BigInteger;
import java.sql.Timestamp;

public class Relationship {

	private BigInteger id;
	private BigInteger user_a_id;
	private BigInteger user_b_id;

	private long request;
	private Timestamp request_time;

	private long result;
	private Timestamp result_time;

	private long relationship;
	private long permission_b;
	private long permission_a;

	private Timestamp gmt_modified;
	private Timestamp gmt_create;
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
	public long getRequest() {
		return request;
	}
	public void setRequest(long request) {
		this.request = request;
	}
	public Timestamp getRequest_time() {
		return request_time;
	}
	public void setRequest_time(Timestamp request_time) {
		this.request_time = request_time;
	}
	public long getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public Timestamp getResult_time() {
		return result_time;
	}
	public void setResult_time(Timestamp result_time) {
		this.result_time = result_time;
	}
	public long getRelationship() {
		return relationship;
	}
	public void setRelationship(Integer relationship) {
		this.relationship = relationship;
	}
	public long getPermission_b() {
		return permission_b;
	}
	public void setPermission_b(Integer permission_b) {
		this.permission_b = permission_b;
	}
	public long getPermission_a() {
		return permission_a;
	}
	public void setPermission_a(Integer permission_a) {
		this.permission_a = permission_a;
	}
	public Timestamp getGmt_modified() {
		return gmt_modified;
	}
	public void setGmt_modified(Timestamp gmt_modified) {
		this.gmt_modified = gmt_modified;
	}
	public Timestamp getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Timestamp gmt_create) {
		this.gmt_create = gmt_create;
	}
	public void setResult(long result) {
		this.result = result;
	}
	public void setRelationship(long relationship) {
		this.relationship = relationship;
	}
	public void setPermission_b(long permission_b) {
		this.permission_b = permission_b;
	}
	public void setPermission_a(long permission_a) {
		this.permission_a = permission_a;
	}
	
}
