package it.univaq.disim.sose.project.shop.business;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import it.univaq.disim.sose.project.shop.business.model.Authentication;
import it.univaq.disim.sose.project.shop.business.model.BankAccount;
import it.univaq.disim.sose.project.shop.business.model.Product;
import it.univaq.disim.sose.project.shop.business.model.Purchase;
import it.univaq.disim.sose.project.shop.business.model.Result;

@Path("/shopservice")
public interface IShopService {	

	@GET
	@Path("products/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProduct(@PathParam("id") int id, @DefaultValue("EUR") @QueryParam("currency") String currency);
	
	@GET
	@Path("products")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProducts(@DefaultValue("EUR") @QueryParam("currency") String currency);
	
	@POST
	@Path("products/{id}/buy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Result buyProduct(@PathParam("id") int id, Purchase purchase);
	
	@POST
	@Path("account")
	@Produces(MediaType.APPLICATION_JSON)
	public BankAccount viewAccount(Authentication account, @DefaultValue("EUR") @QueryParam("currency") String currency);
}