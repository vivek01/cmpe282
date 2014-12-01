package edu.sjsu.cmpe282.api.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.sjsu.cmpe282.dao.UserDao;
import edu.sjsu.cmpe282.domain.Message;
import edu.sjsu.cmpe282.domain.User;

@Path("/us")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Home {

	private UserDao userdao = new UserDao();

	@POST
	@Path("/signup")
	public User signUp(User user){

		String msg="";
		System.out.print("user created: "+user.getFirstName());
		msg=userdao.addUser(user);
		switch(msg){
		case "success":
		{
			user.setMessage("Successfully signed");

			break;
		}


		case "Database Error":
		{
			user.setMessage("database errror");


			break;
		}

		case "field empty" :
		{
			user.setMessage("please fill all the fields");
			break;
		}
		}
		return user;
	}


	@POST
	@Path("/signin")
	public User signIn(User user)
	{    
		String temp= userdao.checkUser(user);
		user.setAuthid(temp);
		System.out.println(temp);
		return user;
	}

	@POST
	@Path("/signout")
	public Message signOut(Message user)
	{    Message msg= new Message();
		String temp=userdao.signOut(user);
		msg.setMsg(temp);
		return msg;
	}
}
