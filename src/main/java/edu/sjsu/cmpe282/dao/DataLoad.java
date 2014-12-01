package edu.sjsu.cmpe282.dao;

import edu.sjsu.cmpe282.domain.Catalog;
import edu.sjsu.cmpe282.domain.Product;

public class DataLoad {
	public static void main(String args[])
	{
		/* AWSDynamo jdbc=new AWSDynamo();
		Catalog catalog= jdbc.scanTable();
		Product[] products=catalog.getProduct();

		for(int i=0; i <products.length;i++)
		{
			System.out.println(products[i].getId());
			System.out.println(products[i].getName());
			System.out.println(products[i].getPrice());
		}*/


		/*RecommendDAO rec= new RecommendDAO();
		Catalog catalog=rec.itemRecommend("12894");
		Product[] products=catalog.getProduct();

		for(int i=0; i <products.length;i++)
		{
			System.out.println(products[i].getId());
		}*/

		RecommendDAO rec= new RecommendDAO();
		Catalog catalog=rec.userRecommend("0");
		Product[] products=catalog.getProduct();

		for(int i=0; i <products.length;i++)
		{
			System.out.println(products[i].getId());
		}
		
	}

}
