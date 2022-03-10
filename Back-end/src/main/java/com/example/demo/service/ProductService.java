package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.Products;
import com.example.demo.payload.ProductIncludeSkuDTO;
import com.example.demo.payload.ProductListDTO;
import com.example.demo.payload.ProductDTO;
import com.example.demo.payload.ProductIncludeImageDTO;

public interface ProductService {
	//Product
	List<ProductDTO> listAllProducts();
	ProductDTO updateProductById(String id, ProductDTO products);
	void deleteProduct(String id);
	ProductIncludeSkuDTO getProductByIdUser(String id);
	ProductListDTO getProductByIdAdmin(String id);
	ProductDTO getProductById(String id);
	
	//Product_SKU
	List<ProductIncludeImageDTO> listAllProductIncludeImage();
	List<Products> listProductBySKUId(Long id);
	void deleteProductSku(Long id);
	Products createProductAll(ProductListDTO productRequest);
	Products createProduct(ProductDTO productRequest);
}
