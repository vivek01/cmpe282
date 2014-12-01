package edu.sjsu.cmpe282.domain;

import java.util.List;

public class Cart{
public List<String> productId;
public String sessionId;
public Cart(List<String> productId, String sessionId) {
	super();
	this.productId = productId;
	this.sessionId = sessionId;
}
public Cart() {
	super();
	// TODO Auto-generated constructor stub
}
public List<String> getProductId() {
	return productId;
}
public void setProductId(List<String> productId) {
	this.productId = productId;
}
public String getSessionId() {
	return sessionId;
}
public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
}



}
