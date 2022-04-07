package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.Product;
import com.example.demo.payload.ProductDetailDTO;
import com.example.demo.payload.ProductListDTO;
import com.example.demo.payload.ProductCreateDTO;
import com.example.demo.payload.ProductDTO;
import com.example.demo.payload.ProductImageDTO;
import com.example.demo.payload.ProductIncludeImageDTO;

public interface ProductService {
	//Product
		List<ProductDTO> listAllProducts();
		ProductDTO updateProductById(String id, ProductDTO products);
		void deleteProduct(String id);
		ProductDetailDTO getProductByIdUser(String id);
		ProductListDTO getProductByIdAdmin(String id);
		ProductDTO getProductById(String id);
		List<ProductIncludeImageDTO> search(String keyword);

		//Product_SKU
		List<ProductIncludeImageDTO> listAllProductIncludeImage();
		List<Product> listProductBySKUId(Long id);
		void deleteProductSku(Long id);
		Product createProductAll(ProductCreateDTO productRequest);
		Product createProduct(ProductDTO productRequest);

}
