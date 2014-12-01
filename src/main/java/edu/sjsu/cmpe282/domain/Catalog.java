package edu.sjsu.cmpe282.domain;

public class Catalog {

	private Product[] product;
    private String msg;
    
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Catalog(Product[] product) {
		super();
		this.product = product;
	}

	public Catalog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product[] getProduct() {
		return product;
	}

	public void setProduct(Product[] product) {
		this.product = product;
	}
	


	
}
