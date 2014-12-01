package edu.sjsu.cmpe282.domain;

public class CartProduct {
	private String Quantity;
	private String Id;
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public CartProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartProduct(String quantity, String id) {
		super();
		Quantity = quantity;
		Id = id;
	}
	
}
