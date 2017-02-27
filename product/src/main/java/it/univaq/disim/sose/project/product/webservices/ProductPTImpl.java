package it.univaq.disim.sose.project.product.webservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.project.product.AllProductsRequest;
import it.univaq.disim.sose.project.product.AllProductsResponse;
import it.univaq.disim.sose.project.product.ProductPT;
import it.univaq.disim.sose.project.product.ProductRequest;
import it.univaq.disim.sose.project.product.ProductResponse;
import it.univaq.disim.sose.project.product.ProductType;
import it.univaq.disim.sose.project.product.UpdateRequest;
import it.univaq.disim.sose.project.product.business.ProductService;
import it.univaq.disim.sose.project.product.business.model.Product;

@Component(value="ProductPTImpl")
public class ProductPTImpl implements ProductPT {

	@Autowired
	private ProductService service;

	@Override
	public ProductResponse product(ProductRequest parameters) {
		try {
			// Retrieve product from DB
			Product product = service.getProduct( parameters.getId() );
			
			// Forge response
			ProductResponse response = new ProductResponse();
			ProductType productType = new ProductType();
			productType.setId( product.getId() );
			productType.setName( product.getName() );
			productType.setDescription( product.getDescription() );
			productType.setImage( product.getImage() );
			productType.setRemainingQuantity( product.getRemainingQuantity() );
			productType.setUnitPrice( product.getUnitPrice() );			
			response.setProduct(productType);
			
			return response;
		} 
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	@Override
	public AllProductsResponse allProducts(AllProductsRequest parameters) {
		try {
			// Retrieve all products from DB
			List<Product> products = service.getProducts();
			
			// Forge response
			AllProductsResponse response = new AllProductsResponse();
			ProductType productType;
			
			for(Product product: products) {
				productType = new ProductType();
				
				productType.setId( product.getId() );
				productType.setName( product.getName() );
				productType.setDescription( product.getDescription() );
				productType.setImage( product.getImage() );
				productType.setRemainingQuantity( product.getRemainingQuantity() );
				productType.setUnitPrice( product.getUnitPrice() );
				
				response.getProducts().add(productType);			
			}
			
			return response;
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	@Override
	public void update(UpdateRequest parameters) {
		try {
			// Create a product from the request parameters
			Product product = new Product();
			product.setId( parameters.getProduct().getId() );
			product.setName( parameters.getProduct().getName() );
			product.setDescription( parameters.getProduct().getDescription() );
			product.setRemainingQuantity( parameters.getProduct().getRemainingQuantity() );
			product.setUnitPrice( parameters.getProduct().getUnitPrice() );
			product.setImage( parameters.getProduct().getImage() );
			
			// Update product in DB 
			service.updateProduct(product);

			return;
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}	
	}	
}
