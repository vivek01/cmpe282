package edu.sjsu.cmpe282.domain;

public class Message {
	String msg;
    String authId;
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Message(String msg, String authId) {
		super();
		this.msg = msg;
		this.authId = authId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}


}
