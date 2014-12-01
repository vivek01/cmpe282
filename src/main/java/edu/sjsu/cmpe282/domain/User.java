package edu.sjsu.cmpe282.domain;

public class User {

	private String firstName;
	private String lastName;
	private String email;
	private String id;
	private String passwd;
	private String time;
	private String message;
	private String authid;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAuthid() {
		return authid;
	}
	public void setAuthid(String authid) {
		this.authid = authid;
	}
	public User(String firstName, String lastName, String email, String id,
			String passwd, String time, String message, String authid) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.id = id;
		this.passwd = passwd;
		this.time = time;
		this.message = message;
		this.authid = authid;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}



}
