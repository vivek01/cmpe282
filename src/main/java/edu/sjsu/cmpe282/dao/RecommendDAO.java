package edu.sjsu.cmpe282.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import edu.sjsu.cmpe282.domain.Catalog;
import edu.sjsu.cmpe282.domain.Message;
import edu.sjsu.cmpe282.domain.Product;
import edu.sjsu.cmpe282.domain.User;

public class RecommendDAO {
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
	public RecommendDAO()
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



	public Catalog itemRecommend(String id )
	{
		Catalog catalog= new Catalog();
		try
		{
			int item=Integer.valueOf(id);
			initialize();
			stmt=conn.createStatement();
			String SQL="select * from p282.recommendation282 where item_id="+item+";";
			System.out.println("SQL for item recommendaton "+SQL);
			ResultSet rs=stmt.executeQuery(SQL);
			List<Product> productsList= new ArrayList<Product>();
			while(rs.next())
			{
				Product product=new Product();
				product.setId(rs.getString("rec_Item"));
				productsList.add(product);
			}
			
			Product[] pro=new Product[productsList.size()];
			pro= productsList.toArray(pro);
			//System.out.println(pro[0].getName());
			catalog.setProduct(pro);

			shutdown();
			return catalog;

		}
		catch(Exception  e)
		{

		}

		return catalog;
	}
	
	
	public Catalog userRecommend(String id )
	{
		Catalog catalog= new Catalog();
		try
		{
			int item=Integer.valueOf(id);
			initialize();
			stmt=conn.createStatement();
			String SQL="select * from p282.r2 where user_id="+item+";";
			System.out.println("SQL for user recommendaton "+SQL);
			ResultSet rs=stmt.executeQuery(SQL);
			List<Product> productsList= new ArrayList<Product>();
			while(rs.next())
			{
				Product product=new Product();
				product.setId(rs.getString("rec_Item"));
				productsList.add(product);
			}
			
			Product[] pro=new Product[productsList.size()];
			pro= productsList.toArray(pro);
			//System.out.println(pro[0].getName());
			catalog.setProduct(pro);

			shutdown();
			return catalog;

		}
		catch(Exception  e)
		{

		}

		return catalog;
	}
	public String getId(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

}
