package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.Products;

public interface ProductService {
	//Product
	List<Products> listAllProducts();
	Products createProduct(Products products);
	Products updateProductById(String id, Products products);
	void deleteProduct(String id);
	Products getProductById(String id);
	
	//Category
	List<Category> listAllCategories();
	List<Products> listProductByCategoryId(Long id);
	
	//Product_SKU
	List<Product_SKU> listAllProductSKU();
	List<Products> listProductBySKUId(Long id);
	Category updateCategoryById(Long id, Category categoryRequest);
	Category createCategory(Category categoryRequest);
	void deleteCategory(Long id);
	void deleteProductSku(Long id);
}
