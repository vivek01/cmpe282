package edu.sjsu.cmpe282.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import edu.sjsu.cmpe282.domain.Cart;
import edu.sjsu.cmpe282.domain.CartDTO;
import edu.sjsu.cmpe282.domain.Catalog;
import edu.sjsu.cmpe282.domain.Message;
import edu.sjsu.cmpe282.domain.Product;
import edu.sjsu.cmpe282.domain.User;

public class CartDAO {

	Connection conn = null;
	Statement stmt = null;

	// Constructure with JDBC connection
	public void initialize()
	{
		try{
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://project282.c01jcgjqoxok.us-west-1.rds.amazonaws.com:3306","admin","password");
			System.out.println("COnn:"+conn);
		}
		catch (SQLException e) {
			e.printStackTrace();

		}

		System.out.println("Connection open to MYSQL");
	}
	public CartDAO()
	{

	}

	public void shutdown()
	{
		try{
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/////////////////////CART DAO

	UserDao mysql=new UserDao();
	AWSDynamo dynamo=new AWSDynamo();

	public   void remove(CartDTO product) {
		mysql=new UserDao();
		try {
			initialize();
			String msg="";
			String sessionId=product.getSessionId();
			msg=mysql.checkAuth(sessionId);
			stmt=conn.createStatement();
			if(msg.equalsIgnoreCase("Success"))
			{
				String productId=product.getProductId();
				String SQL="delete from test.cart where sessionid='"+sessionId+"' and productid='"+productId+"';";
				System.out.println("SQL for deleting from cart:"+SQL);
				stmt.executeUpdate(SQL);
			}
			shutdown();
		}   catch (SQLException ase) {
			ase.printStackTrace();
		} 

	}

	public   void add(CartDTO product) {
		mysql=new UserDao();
		try {
			initialize();
			stmt=conn.createStatement();
			String msg="";
			String sessionId=product.getSessionId();
			msg=mysql.checkAuth(sessionId);
			if(msg.equalsIgnoreCase("Success"))
			{

				String productId=product.getProductId();
				String SQL="insert into test.cart values('"+sessionId+"','"+productId+"');";
				System.out.println("SQL for inserting ino cart "+SQL);
				stmt.executeUpdate(SQL);
			}
			shutdown();
		}   catch (SQLException ase) {
			ase.printStackTrace();
		} 

	}
	//Method to display all items from the cart
	public  Cart getProduct(String id) {

		Cart cart=new Cart();
		try
		{initialize();
		stmt=conn.createStatement();
		String SQL="select * from test.cart where sessionid='"+id+"';";
		ResultSet rs=stmt.executeQuery(SQL);
		List<String> products= new ArrayList<String>();
		while(rs.next())
		{
			String prodid=rs.getString("productid");
			products.add(prodid);
		}
		cart.setProductId(products);
		shutdown();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return cart;
	}

	

// Get item name from the cart
	public Catalog itemPurchase(String authId) 
	{
		Catalog catalog=new Catalog();
		try
		{
			Cart cart=new Cart();
			cart=getProduct(authId);
			// Fetch the product id of the product in the shopping cart
			List<String> list=	cart.getProductId();

			catalog=dynamo.listProduct(list);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return catalog;
	}

	public Catalog viewCart(String authId) 
	{
		Catalog catalog=new Catalog();
		ResultSet rs;
		try
		{
			initialize();
			stmt=conn.createStatement();
			Cart cart=new Cart();
			cart=getProduct(authId);
			// Fetch the product id of the product in the shopping cart
			List<String> list=	cart.getProductId();

			catalog=dynamo.listProduct(list);
			;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return catalog;
	}

	public void deleteCart(String id) {

		try
		{ 
			initialize();
			stmt=conn.createStatement();
			String SQL="delete from test.cart where sessionid='"+id+"';";
			stmt.executeUpdate(SQL);

		}
		catch(Exception e )
		{
			e.printStackTrace();
		}

	}

}
