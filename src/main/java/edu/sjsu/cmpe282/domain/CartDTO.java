package edu.sjsu.cmpe282.domain;

public class CartDTO {
public String sessionId;
public String productId;
public String getSessionId() {
	return sessionId;
}
public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
}
public String getProductId() {
	return productId;
}
public void setProductId(String productId) {
	this.productId = productId;
}
public CartDTO() {
	super();
	// TODO Auto-generated constructor stub
}
public CartDTO(String sessionId, String productId) {
	super();
	this.sessionId = sessionId;
	this.productId = productId;
}

}
