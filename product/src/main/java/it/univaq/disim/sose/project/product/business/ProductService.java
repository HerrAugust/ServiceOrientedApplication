package it.univaq.disim.sose.project.product.business;

import java.util.List;

import it.univaq.disim.sose.project.product.business.model.Product;

public interface ProductService {
	Product getProduct(int id) throws BusinessException;
	List<Product> getProducts() throws BusinessException;
	void updateProduct(Product product) throws BusinessException;
}
