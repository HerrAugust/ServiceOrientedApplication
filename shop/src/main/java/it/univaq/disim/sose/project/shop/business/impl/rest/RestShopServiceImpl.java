package it.univaq.disim.sose.project.shop.business.impl.rest;

import it.univaq.disim.sose.project.clients.bank.AccountRequest;
import it.univaq.disim.sose.project.clients.bank.AccountResponse;
import it.univaq.disim.sose.project.clients.bank.BankPT;
import it.univaq.disim.sose.project.clients.bank.BankService;
import it.univaq.disim.sose.project.clients.bank.LoginType;
import it.univaq.disim.sose.project.clients.bank.WithdrawRequest;
import it.univaq.disim.sose.project.clients.bank.WithdrawResponse;
import it.univaq.disim.sose.project.clients.currency.CurrencyPT;
import it.univaq.disim.sose.project.clients.currency.CurrencyService;
import it.univaq.disim.sose.project.clients.currency.CurrencyType;
import it.univaq.disim.sose.project.clients.currency.RateRequest;
import it.univaq.disim.sose.project.clients.currency.RateResponse;
import it.univaq.disim.sose.project.clients.delivery.AddressType;
import it.univaq.disim.sose.project.clients.delivery.DeliveryFeeRequest;
import it.univaq.disim.sose.project.clients.delivery.DeliveryFeeResponse;
import it.univaq.disim.sose.project.clients.delivery.DeliveryPT;
import it.univaq.disim.sose.project.clients.delivery.DeliveryService;
import it.univaq.disim.sose.project.clients.product.AllProductsRequest;
import it.univaq.disim.sose.project.clients.product.AllProductsResponse;
import it.univaq.disim.sose.project.clients.product.ProductPT;
import it.univaq.disim.sose.project.clients.product.ProductRequest;
import it.univaq.disim.sose.project.clients.product.ProductResponse;
import it.univaq.disim.sose.project.clients.product.ProductService;
import it.univaq.disim.sose.project.clients.product.ProductType;
import it.univaq.disim.sose.project.clients.product.UpdateRequest;
import it.univaq.disim.sose.project.shop.business.IShopService;
import it.univaq.disim.sose.project.shop.business.model.Address;
import it.univaq.disim.sose.project.shop.business.model.Authentication;
import it.univaq.disim.sose.project.shop.business.model.BankAccount;
import it.univaq.disim.sose.project.shop.business.model.Product;
import it.univaq.disim.sose.project.shop.business.model.Purchase;
import it.univaq.disim.sose.project.shop.business.model.Result;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("shopService")
public class RestShopServiceImpl implements IShopService {

	private static Logger LOGGER = LoggerFactory.getLogger(RestShopServiceImpl.class);	
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
		
		// Get product
		ProductService productService = new ProductService();
		ProductPT productPT = productService.getProductPort();
		ProductRequest productRequest = new ProductRequest();
		productRequest.setId(id);
		ProductResponse productResponse = productPT.product(productRequest);
		
		// Get currency rate
		CurrencyType currencyType = toCurrency(currency);
		double rate = 1;
		// No conversion needed if EUR is used
		if( !currencyType.equals(CurrencyType.EUR) ) {
			CurrencyService currencyService = new CurrencyService();
			CurrencyPT currencyPT = currencyService.getCurrencyPort();		
			RateRequest rateRequest = new RateRequest();
			rateRequest.setFrom(CurrencyType.EUR);
			rateRequest.setTo(currencyType);
			RateResponse rateResponse = currencyPT.rate(rateRequest);
			rate = rateResponse.getRate();
		}
		
		// Forge response
		Product response = new Product();
		response.setId( productResponse.getProduct().getId() );
		response.setName( productResponse.getProduct().getName() );
		response.setDescription( productResponse.getProduct().getDescription() );
		response.setRemainingQuantity( productResponse.getProduct().getRemainingQuantity() );
		response.setUnitPrice( productResponse.getProduct().getUnitPrice()*rate );
		response.setImage( productResponse.getProduct().getImage() );		

		return response;
	}
	
	/**
	 * Returns the list of all products available in the shop
	 */
	@Override
	public List<Product> getProducts(String currency) {
		LOGGER.info("GET /products?currency=" + currency);
		
		// Get product list
		ProductService productService = new ProductService();
		ProductPT productPT = productService.getProductPort();
		AllProductsRequest allProductRequest = new AllProductsRequest();
		AllProductsResponse allProductResponse = productPT.allProducts(allProductRequest);
		
		// Get currency rate
		CurrencyType currencyType = toCurrency(currency);
		double rate = 1;
		// No conversion needed if EUR is used
		if( !currencyType.equals(CurrencyType.EUR) ) {
			CurrencyService currencyService = new CurrencyService();
			CurrencyPT currencyPT = currencyService.getCurrencyPort();		
			RateRequest rateRequest = new RateRequest();
			rateRequest.setFrom(CurrencyType.EUR);
			rateRequest.setTo(currencyType);
			RateResponse rateResponse = currencyPT.rate(rateRequest);
			rate = rateResponse.getRate();
		}

		// Forge response
		List<Product> response = new ArrayList<Product>();
		
		for(ProductType productType: allProductResponse.getProducts()) {
			Product product = new Product();
			
			product.setId( productType.getId() );
			product.setName( productType.getName() );
			product.setDescription( productType.getDescription() );
			product.setRemainingQuantity( productType.getRemainingQuantity() );
			product.setUnitPrice( productType.getUnitPrice()*rate );
			product.setImage( productType.getImage() );
			
			response.add(product);
		}
		
		return response;
	}
	@Override
	public Result buyProduct(int id, Purchase purchase) {
		LOGGER.info("POST /products/" + id + "/buy");
		Result response = new Result();
		
		// Get account
		BankService bankService = new BankService();
		BankPT bankPT = bankService.getBankPort();
		AccountRequest accountRequest = new AccountRequest();
		
		LoginType login = new LoginType();
		login.setUsername( purchase.getAccount().getUsername() );
		login.setPassword( purchase.getAccount().getPassword() );
		accountRequest.setAccount(login);
		
		AccountResponse accountResponse = bankPT.account(accountRequest);
		
		// Get item
		ProductService productService = new ProductService();
		ProductPT productPT = productService.getProductPort();
		ProductRequest productRequest = new ProductRequest();
		productRequest.setId( purchase.getItemID() ); // NB: can also use 'id'
		ProductResponse productResponse = productPT.product(productRequest);
		
		// Is there enough items?
		if(productResponse.getProduct().getRemainingQuantity()-purchase.getQuantity()<=0) {
			response.setSuccess(false);
			response.setMessage("Not enough product available");
			return response;
		}
		
		// Get delivery fee
		DeliveryService deliveryService = new DeliveryService();
		DeliveryPT deliveryPT = deliveryService.getDeliveryPort();
		DeliveryFeeRequest deliveryRequest = new DeliveryFeeRequest();
		
		AddressType origin = new AddressType();
		origin.setStreet(this.street);
		origin.setCity(this.city);
		origin.setCountry(this.country);
		
		AddressType destination = new AddressType();
		destination.setStreet( accountResponse.getAddress().getStreet() );
		destination.setCity( accountResponse.getAddress().getCity() );
		destination.setCountry( accountResponse.getAddress().getCountry() );
		
		deliveryRequest.setOrigin(origin);
		deliveryRequest.setDestination(destination);

		DeliveryFeeResponse deliveryResponse = deliveryPT.deliveryFee(deliveryRequest);
		
		// Business logic		
		double amount = productResponse.getProduct().getUnitPrice()*purchase.getQuantity() + deliveryResponse.getDeliveryFee();
		
		if(amount>accountResponse.getAmount()) {
			response.setSuccess(false);
			response.setMessage("You dont have enough money");
			return response;
		}
		
		// Update product quantity
		UpdateRequest updateProduct = new UpdateRequest();
		productResponse.getProduct().setRemainingQuantity( 
				productResponse.getProduct().getRemainingQuantity()-purchase.getQuantity() );
		updateProduct.setProduct( productResponse.getProduct() );
		productPT.update(updateProduct);
		
		// Withdraw
		WithdrawRequest withdrawRequest = new WithdrawRequest();
		withdrawRequest.setAmount(amount);		
		withdrawRequest.setAccount(login); // Reuse
		WithdrawResponse withdrawResponse = bankPT.withdraw(withdrawRequest);
		
		// Reply
		if( withdrawResponse.getResult().isSuccess() ) {
			response.setSuccess(true);
			response.setMessage("Product successfully bought");
		} else {
			response.setSuccess(false);
			response.setMessage(withdrawResponse.getResult().getResult());
		}
		
		return response;
	}
	
	/**
	 * Retrieve a bank account from an authentication
	 */
	@Override
	public BankAccount viewAccount(Authentication account, String currency) {
		LOGGER.info("GET /account?currency=" + currency);
		
		// Get account
		BankService bankService = new BankService();
		BankPT bankPT = bankService.getBankPort();
		AccountRequest accountRequest = new AccountRequest();		
		LoginType login = new LoginType();
		login.setUsername( account.getUsername() );
		login.setPassword( account.getPassword() );
		accountRequest.setAccount(login);		
		AccountResponse accountResponse = bankPT.account(accountRequest);		

		// Get currency rate
		CurrencyType currencyType = toCurrency(currency);
		double rate = 1;
		// No conversion needed if EUR is used
		if( !currencyType.equals(CurrencyType.EUR) ) {
			CurrencyService currencyService = new CurrencyService();
			CurrencyPT currencyPT = currencyService.getCurrencyPort();		
			RateRequest rateRequest = new RateRequest();
			rateRequest.setFrom(CurrencyType.EUR);
			rateRequest.setTo(currencyType);
			RateResponse rateResponse = currencyPT.rate(rateRequest);
			rate = rateResponse.getRate();
		}
		
		// Response
		BankAccount response = new BankAccount();
		response.setAmount( accountResponse.getAmount()*rate );
		response.setUsername( account.getUsername() );
		response.setFirstname( accountResponse.getName().getFirstname()  );
		response.setLastname( accountResponse.getName().getLastname() );
		Address address = new Address();
		address.setStreet( accountResponse.getAddress().getStreet() );
		address.setCity( accountResponse.getAddress().getCity() );
		address.setCountry( accountResponse.getAddress().getCountry() );
		response.setAddress(address);
		
		return response;
	}
	
	/**
	 * Convert a currency string into a CurrencyType
	 * @param currency
	 * @return currencyType
	 */
	private static CurrencyType toCurrency(String currency) {
		switch(currency) {
			case "EUR": return CurrencyType.EUR;
			case "GBP": return CurrencyType.GBP;
			case "USD": return CurrencyType.USD;
			default: return CurrencyType.EUR;
		}
	}
}