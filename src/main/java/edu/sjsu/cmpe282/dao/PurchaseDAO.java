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
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import edu.sjsu.cmpe282.domain.Card;
import edu.sjsu.cmpe282.domain.Cart;
import edu.sjsu.cmpe282.domain.Catalog;
import edu.sjsu.cmpe282.domain.History;
import edu.sjsu.cmpe282.domain.Message;
import edu.sjsu.cmpe282.domain.Product;
import edu.sjsu.cmpe282.domain.User;

public class PurchaseDAO {
	Connection conn = null;
	Statement stmt = null;
	UserDao mysql=new UserDao();
	CartDAO cart=new CartDAO();
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
	public PurchaseDAO()
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



	////////////////////////////////// Dynamo replacement code
	public  Catalog scanTable()
	{ 
		Catalog catalog =new Catalog();
		initialize();
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			String query = "Select * from test.ProductCatalog;";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			ArrayList<Product> products=new ArrayList<Product>();
			while(rs.next())
			{
				Product product=new Product();
				product.setId(rs.getString("Id"));
				//System.out.println(rs.getString("Id"));
				product.setName(rs.getString("name"));
				//System.out.println(rs.getString("name"));
				product.setPrice(rs.getString("price"));
				//System.out.println(rs.getString("price"));
				products.add(product);
			}
			//System.out.println(products.size());
			Product[] pro=new Product[products.size()];

			pro= products.toArray(pro);
			//System.out.println(pro[0].getName());
			catalog.setProduct(pro);

			shutdown();
			return catalog;
		} 

		catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return catalog;
		}
		catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return catalog;
		}

	}



	/////Methods to be added
	public   Catalog purchase(Card card)
	{
		Cart cartObject=new Cart();
		Catalog catalogObject=new Catalog();
		User user=new User();
		ResultSet rs;
		try {
			initialize();

			String msg="";
			String sessionId=card.getSessionId();


			// Check if session exists
			msg=mysql.checkAuth(sessionId);
			if(msg.equalsIgnoreCase("Success"))
			{   
				user=mysql.userInfo(sessionId);
				String id=user.getId();
				System.out.println("User info retrieved from database user id "+id);
				// Add  the user name, email to purchase table
				stmt=conn.createStatement();

				// fetch productId from cart and get them in an array and put them in the array in ss
				cartObject=cart.getProduct(sessionId);
				List<String> list=cartObject.getProductId();
				for(int i=0;i<list.size();i++)
				{
					String SQL="insert into test.purchase values('"+id+"','"+list.get(i)+"');";
					stmt.executeUpdate(SQL);
				}

				System.out.println("Purchase recoded");

			}

		}   catch (SQLException ase) {
			ase.printStackTrace();
		} 

		return catalogObject;
	}


	public History showPurchase(Message aid)
	{

		History history=new History();

		User user=new User();
		try {
			initialize();
			stmt=conn.createStatement();
			String msg="";
			String sessionId=aid.getAuthId();
			// Check if session exists
			msg=mysql.checkAuth(sessionId);
			if(msg.equalsIgnoreCase("Success"))
			{   
				user=mysql.userInfo(sessionId);
				String id=user.getId();
				System.out.println("User info retrieved from database user id: "+id);
				String SQL="select * from test.purchase where uid='"+id+"';";
				ResultSet rs=stmt.executeQuery(SQL);
				List<String> ls=new ArrayList<String>();
				while(rs.next())
				{
					ls.add(rs.getString("productid"));
				}
				
				String[] typo=new String[ls.size()];
				int index=0;
				for(String s: ls)
				{
					typo[index]=s;
					index++;
				}

				history.setName(typo);
			}

		}   catch (SQLException ase) {
			System.err.println("Failed to create item in " + "cart" + " " + ase);
		} 
		return history;
	}

}


