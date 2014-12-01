package edu.sjsu.cmpe282.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.sjsu.cmpe282.dao.RecommendDAO;
import edu.sjsu.cmpe282.dao.UserDao;
import edu.sjsu.cmpe282.domain.CartDTO;
import edu.sjsu.cmpe282.domain.Catalog;
import edu.sjsu.cmpe282.domain.Item;
import edu.sjsu.cmpe282.domain.Message;


@Path("/recommend")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecommendationResource {
	RecommendDAO recommend= new RecommendDAO();
	UserDao userDAO=new UserDao();
	@POST
	@Path("/user/")
	public Catalog userRecommend(Message message)
	{
		Catalog catalog= new Catalog();
		String autid=message.getAuthId();
		String id=userDAO.getId(autid);
		catalog=recommend.userRecommend(id);
		
				return catalog;
	}

	
	@POST
	@Path("/item/")
	public Catalog itemRecommend(Item item)
	{
		Catalog catalog= new Catalog();
	  
		catalog=recommend.userRecommend(item.getItemId());
		
				return catalog;
	}
}
