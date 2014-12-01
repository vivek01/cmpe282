package edu.sjsu.cmpe282.domain;

public class Card {
public String number;
public String CVV;
public String sessionId;
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
public String getCVV() {
	return CVV;
}
public void setCVV(String cVV) {
	CVV = cVV;
}
public String getSessionId() {
	return sessionId;
}
public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
}
public Card(String number, String cVV, String sessionId) {
	super();
	this.number = number;
	CVV = cVV;
	this.sessionId = sessionId;
}
public Card() {
	super();
	// TODO Auto-generated constructor stub
}

}
