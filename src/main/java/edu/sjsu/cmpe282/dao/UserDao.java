package edu.sjsu.cmpe282.dao;

import java.sql.*;
import java.util.UUID;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import edu.sjsu.cmpe282.domain.Message;
import edu.sjsu.cmpe282.domain.User;

public class UserDao {
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
	public UserDao()
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

	public String addUser(User user)
	{  
		initialize();
		ResultSet rs;
		try {

			stmt = conn.createStatement();
			String query = "Select * from test.users where email = '"+user.getEmail()+"';";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			if(!rs.next())
			{
				String id = UUID.randomUUID().toString();
				stmt = conn.createStatement();
				String date=new java.util.Date().toString();
				String query1 = "INSERT INTO `test`.`users`  VALUES ('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getEmail() + "', '" + user.getPasswd() +"', '"+date+"','"+user.getId()+"');";
				System.out.println(query1);
				stmt.executeUpdate(query1);
				String uid_insert="insert into `test`.`uid` values('"+user.getEmail()+"','"+"null"+"')";;
				System.out.println(uid_insert);
				stmt.executeUpdate(uid_insert);
				shutdown();
			}
			return "success";
		} 

		catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return "Database Error";
		}
		catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "field empty";
		}



	}


	public String checkUser(User user){
		initialize();
		ResultSet rs;
		String id="unauthorized transaction";
		String origPasswd = null;
		try {
			stmt = conn.createStatement();
			String query = "Select password from test.users where email = '"+user.getEmail()+"';";
			rs = stmt.executeQuery(query);
			rs.next();
			origPasswd = rs.getString("password");
			System.out.println("Password from db : "+ origPasswd );
			System.out.println("Password entered : "+user.getPasswd());
			if (user.getPasswd().equals(origPasswd))
			{
				id = UUID.randomUUID().toString();
				System.out.println(id);
				String date=new java.util.Date().toString();
				String uid_update="update  test.uid set id ='"+id+"' where email='"+user.getEmail()+"'";
				stmt.executeUpdate(uid_update);
				String time_update="update test.users set time='"+date+"' where email='"+user.getEmail()+"'";
				stmt.executeUpdate(time_update);
				;
				System.out.println("inside checking the user name");
				shutdown();
			}
			else
			{
				System.out.println("user name and password doesnt match");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;
	}


	public String checkAuth(String id)
	{
		initialize();
		ResultSet rs;
		String message="Failure";

		try {
			stmt = conn.createStatement();
			String query = "Select email from test.uid where id = '"+id+"';";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			if(rs.next())
			{
				System.out.println("user authenticated");
				message="Success";
			}
			shutdown();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}

	public String signOut(Message user)
	{
		initialize();
		Statement stmt ;
		String message="";
		try{
			stmt= conn.createStatement();
			String authid=user.getAuthId();
			System.out.println(authid);
			String uid_update="update  test.uid set id ='"+"null"+"' where id='"+authid+"'";
			stmt.executeUpdate(uid_update);
			message="success";
			CartDAO cart=new CartDAO();
			cart.deleteCart(authid);
			shutdown();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return message;
	}
	//
	//	public void userHistory(String sid)
	//	{
	//		
	//		ResultSet rs;
	//		String id="unauthorized transaction";
	//		String origPasswd = null;
	//		try {
	//			stmt = conn.createStatement();
	//			String query = "Select password from cloudservices.users where email = '"+user.getEmail()+"';";
	//			rs = stmt.executeQuery(query);
	//			rs.next();
	//			origPasswd = rs.getString("password");
	//			System.out.println("Password from db : "+ origPasswd );
	//			System.out.println("Password entered : "+user.getPasswd());
	//			if (user.getPasswd().equals(origPasswd))
	//			{
	//				id = UUID.randomUUID().toString();
	//				System.out.println(id);
	//				String date=new java.util.Date().toString();
	//				String uid_update="update  uid set id ='"+id+"' where email='"+user.getEmail()+"'";
	//				stmt.executeUpdate(uid_update);
	//				String time_update="update users set time='"+date+"' where email='"+user.getEmail()+"'";
	//				stmt.executeUpdate(time_update);
	//				;
	//			}
	//
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//
	//		return id;
	//		
	//	}
	public String userName(Message user)
	{
		initialize();
		ResultSet rs;
		Statement stmt ;
		String message="";
		try{
			stmt= conn.createStatement();
			String authid=user.getAuthId();
			System.out.println(authid);
			String uid="select firstname from test.users where email=(select email from test.uid where id='"+authid+"');";
			System.out.println(uid);
			rs=stmt.executeQuery(uid);
			//			if(!rs.next())
			//			{
			rs.next();
			message=rs.getString("firstname");
			//			}
			//			else
			//			{
			//				message="Admin";
			//			}

			shutdown();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return message;
	}


	public User userInfo(String authid)
	{  
		initialize();
		ResultSet rs;
		Statement stmt ;
		User user=new User();;
		try{
			stmt= conn.createStatement();

			System.out.println(authid);
			String uid="select * from test.users where email=(select email from test.uid where id='"+authid+"');";
			System.out.println(uid);
			rs=stmt.executeQuery(uid);

			rs.next();
			user.setFirstName(rs.getString("firstname"));
			user.setLastName(rs.getString("lastName"));
			user.setEmail(rs.getString("email"));
			user.setTime(rs.getString("time"));
			user.setId(rs.getString("id"));
			shutdown();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return user;
	}


	public String getId(String uid) {
		initialize();
		ResultSet rs;
		Statement stmt ;
		String message="";
		try{
			stmt= conn.createStatement();
			String SQL="select email from test.uid where id='"+uid+"';";
			System.out.println(SQL);
			rs=stmt.executeQuery(SQL);
			//			if(!rs.next())
			//			{
			rs.next();
			message=rs.getString("email");
			shutdown();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return message;
	}
}
