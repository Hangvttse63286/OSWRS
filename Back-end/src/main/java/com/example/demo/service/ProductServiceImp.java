package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.Products;
import com.example.demo.payload.ProductDTO;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSKURepository;

import javassist.NotFoundException;

@Service
public class ProductServiceImp implements ProductService{

	private final ProductSKURepository productSKURepository;
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	
	public ProductServiceImp(ProductRepository productRepository, CategoryRepository categoryRepository, ProductSKURepository productSKURepository) {
		super();
		this.productRepository= productRepository;
		this.categoryRepository= categoryRepository;
		this.productSKURepository= productSKURepository;
	}
	
	//Create product - Admin
	@Override
	public Products createProduct(Products productRequest) {
		return productRepository.save(productRequest);
	}
	
	//Create category - Admin
	@Override
	public Category createCategory(Category categoryRequest) {
		return categoryRepository.save(categoryRequest);
	}

	//Update Product - Admin
	@Override
	public Products updateProductById(String id, Products productRequest) {
		Products products= productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		products.setDescription_details(productRequest.getDescription_details());
		products.setDescription_list(productRequest.getDescription_list());
		products.setDiscount_id(productRequest.getDiscount_id());
		products.setProduct_name(productRequest.getProduct_name());
		products.setProduct_status_id(productRequest.getProduct_status_id());
		products.setSearch_word(productRequest.getSearch_word());
		return productRepository.save(products);
	}
	
	//Update Category - Admin
	@Override
	public Category updateCategoryById(Long id, Category categoryRequest) {
		Category category= categoryRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		category.setCategory_name(categoryRequest.getCategory_name());
		category.setIs_deleted(categoryRequest.isIs_deleted());
		return categoryRepository.save(category);
	}

	//Delete PRoduct - Admin
	@Override
	public void deleteProduct(String id) {
		Products products= productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		productRepository.delete(products);
	}
	
	//Delete Category - Admin
	@Override
	public void deleteCategory(Long id) {
		Category category= categoryRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		categoryRepository.delete(category);
	}
	
	//Delete Product-sku - Admin
	@Override
	public void deleteProductSku(Long id) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		productSKURepository.delete(product_SKU);
	}

	//List Product - User
	@Override
	public List<Products> listAllProducts() {
		return productRepository.findAll();
	}
	
	@Override
	public Products getProductById(String id) {
		Optional<Products> resultOptional= productRepository.findById(id);
		if(resultOptional.isPresent()) {
			return resultOptional.get();
		}
		else {
			throw new NullPointerException("Error: No object found.");
		}
	}

	@Override
	public List<Category> listAllCategories() {
		return categoryRepository.findAll();
	}
	

	@Override
	public List<Products> listProductByCategoryId(Long id) {
		List<Products> resultOptional= productRepository.findByCategoriesId(id); 
		if(resultOptional.isEmpty()) {
			throw new NullPointerException("Error: No object found.");
		}
		else {
			return resultOptional;
		}
	}

	@Override
	public List<Product_SKU> listAllProductSKU() {
		return productSKURepository.findAll();
	}

	@Override
	public List<Products> listProductBySKUId(Long id) {
		List<Products> resultOptional= productRepository.findByProductSKUsId(id); 
		if(resultOptional.isEmpty()) {
			throw new NullPointerException("Error: No object found.");
		}
		else {
			return resultOptional;
		}
	}


	
}
