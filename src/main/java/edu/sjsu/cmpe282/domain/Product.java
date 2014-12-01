package edu.sjsu.cmpe282.domain;

public class Product {
	private String Id;
	private String name;
	private String Price;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public Product(String id, String name, String price) {
		super();
		Id = id;
		this.name = name;
		Price = price;
	}
	public Product()
	{
		super();
	}
}
