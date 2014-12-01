package edu.sjsu.cmpe282.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import edu.sjsu.cmpe282.domain.Catalog;
import edu.sjsu.cmpe282.domain.Message;
import edu.sjsu.cmpe282.domain.Product;
import edu.sjsu.cmpe282.domain.User;

public class AWSDynamo {
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
	public AWSDynamo()
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


	public Catalog listProduct(List<String> list) 
	{  
		Catalog catalog=new Catalog();
		Product[] product=new Product[list.size()];
		System.out.println("size of list "+list.size());
		int index=0;
		try
		{
			initialize();
			for(String id: list)
			{ stmt=conn.createStatement();
			System.out.println(id);

			String SQL="select * from test.ProductCatalog where Id='"+id+"';";
			ResultSet rs=stmt.executeQuery(SQL);
			while(rs.next())
			{
				System.out.println(index);
				product[index]=new Product();
				product[index].setName(rs.getString("name"));
				product[index].setPrice(rs.getString("price"));
				product[index].setId(rs.getString("Id"));
				index++;
			}
			}

			catalog.setProduct(product);

			shutdown();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return catalog;
	}
}
