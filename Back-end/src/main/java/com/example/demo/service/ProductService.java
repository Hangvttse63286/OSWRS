package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Product;

public interface ProductService {
	List<Product> listAllProducts();
	Product createProduct(Product product);
	Product updateProductById(String id, Product product);
	void deleteProduct(String id);
	Product getProductById(String id);
}
