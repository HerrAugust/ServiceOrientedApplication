package it.univaq.disim.sose.project.shop.business.impl.dummy;

import it.univaq.disim.sose.project.shop.business.IShopService;
import it.univaq.disim.sose.project.shop.business.model.Address;
import it.univaq.disim.sose.project.shop.business.model.Authentication;
import it.univaq.disim.sose.project.shop.business.model.BankAccount;
import it.univaq.disim.sose.project.shop.business.model.Product;
import it.univaq.disim.sose.project.shop.business.model.Purchase;
import it.univaq.disim.sose.project.shop.business.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("shopService")
public class DummyShopServiceImpl implements IShopService {

	private static Logger LOGGER = LoggerFactory.getLogger(DummyShopServiceImpl.class);	
	private static int MIN = 3;
	private static int MAX = 20;
	
	@Value("#{cfg.street}")
	private String street;
	@Value("#{cfg.city}")
	private String city;
	@Value("#{cfg.country}")
	private String country;
	
	/**
	 * Return a product based on its ID
	 */
	@Override
	public Product getProduct(int id, String currency) {	
		LOGGER.info("GET /products/" + id + "?currency=" + currency);
		Product product = new Product();
		product.setId(id);
		product.setName("name " + id);
		product.setDescription("description " + id);
		product.setRemainingQuantity(id);
		product.setUnitPrice(id);
		product.setImage("img/product.png");
		return product;
	}

	/**
	 * Returns the list of all products available in the shop
	 */
	@Override
	public List<Product> getProducts(String currency) {
		LOGGER.info("GET /products?currency=" + currency);
		
		List<Product> response = new ArrayList<Product>();
		Product product;
		int nbIterations = (new Random()).nextInt(MAX - MIN + 1) + MIN;
		
		for(int i=0; i<nbIterations; ++i) {
			product = new Product();
			
			product.setId(i);
			product.setName("name " + i);
			product.setDescription("description " + i);
			product.setRemainingQuantity(i);
			product.setUnitPrice(i);
			product.setImage("img/product.png");
			
			response.add(product);
		}
		
		return response;
	}

	/**
	 * Purchase an object and returns the result of the operation
	 */
	@Override
	public Result buyProduct(int id, Purchase purchase) {
		LOGGER.info(
				"buyProduct(id=" + purchase.getItemID()
				+ ",quantity=" + purchase.getQuantity()
				+ ",username=" + purchase.getAccount().getUsername()
				+ ",password=" + purchase.getAccount().getPassword()
				+ ")");		
				
		Result response = new Result();	
		
		if(purchase.getItemID()==5) {
			response.setSuccess(false);
			response.setMessage("You dont have enough money");
		} else {
			response.setSuccess(true);
			response.setMessage("Item bought");			
		}
				
		return response;
	}

	/**
	 * Retrieve the client bank account
	 */
	@Override
	public BankAccount viewAccount(Authentication account, String currency) {
		LOGGER.info("POST /accounts?currency=" + currency);	
		
		BankAccount response = new BankAccount();
		
		response.setFirstname("myFirstName");
		response.setLastname("MyLastName");
		response.setUsername( account.getUsername() );
		response.setAmount( Math.random()*100 );
		
		Address address = new Address();
		address.setStreet("myStreet");
		address.setCity("myCity");
		address.setCountry("myCountry");		
		response.setAddress(address);
		
		return response;
	}
}