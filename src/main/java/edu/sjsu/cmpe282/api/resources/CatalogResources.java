package edu.sjsu.cmpe282.api.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.sjsu.cmpe282.dao.AWSDynamo;
import edu.sjsu.cmpe282.dao.CartDAO;
import edu.sjsu.cmpe282.dao.PurchaseDAO;
import edu.sjsu.cmpe282.dao.UserDao;
import edu.sjsu.cmpe282.domain.Card;
import edu.sjsu.cmpe282.domain.CartDTO;
import edu.sjsu.cmpe282.domain.Catalog;
import edu.sjsu.cmpe282.domain.History;
import edu.sjsu.cmpe282.domain.Message;
import edu.sjsu.cmpe282.domain.User;

@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CatalogResources {

	AWSDynamo dynamo= new AWSDynamo();
	CartDAO cart=new CartDAO();
	PurchaseDAO purchase= new PurchaseDAO();

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Catalog viewItems()
	{
		Catalog catalog=new Catalog();
		catalog=dynamo.scanTable();
		return catalog;


	}

	@POST
	@Path("/cart/")
	public Message add(CartDTO product)
	{
		Message msg =new Message();
		cart.add(product);
		msg.setMsg("Successfully Added product to the cart");
		msg.setAuthId(product.getSessionId());
		return msg;
	}
	@DELETE
	@Path("/cart/")
	public Message remove(CartDTO product)
	{
		Message msg =new Message();
		cart.remove(product);
		msg.setMsg("Successfully removed product from the cart");
		msg.setAuthId(product.getSessionId());
		return msg;
	}
	@POST
	@Path("/checkout")
	public Catalog purchase(Card card)
	{   

		Message msg=new Message();
		String sid=card.getSessionId();
		Catalog catalog=  purchase.purchase(card);
		catalog.setMsg("success");

		return catalog;
	}
	@POST
	@Path("/cart/view")
	public Catalog viewCart(Message msg)
	{
		Catalog catalog=new Catalog();
		catalog=cart.viewCart(msg.getAuthId());
		return catalog;


	}
	@POST
	@Path("/history")
	public History history(Message msg)
	{
		History history=new History();
		history=purchase.showPurchase(msg);
		history.setMsg("success");
		return history;


	}

}
