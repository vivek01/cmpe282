package edu.sjsu.cmpe282.domain;

import java.util.List;

public class History {
	String[] name;
	String msg;
	public String[] getName() {
		return name;
	}
	public void setName(String[] name) {
		this.name = name;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public History(String[] name, String msg) {
		super();
		this.name = name;
		this.msg = msg;
	}
	public History() {
		super();
		// TODO Auto-generated constructor stub
	}


}
