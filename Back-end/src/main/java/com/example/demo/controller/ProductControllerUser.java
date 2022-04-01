package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.ProductDTO;
import com.example.demo.payload.ProductIncludeImageDTO;
import com.example.demo.payload.ProductDetailDTO;
import com.example.demo.payload.ProductListDTO;
import com.example.demo.service.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product/")
public class ProductControllerUser {

	private final ProductService productService;


	public ProductControllerUser(ProductService productService) {
		super();
		this.productService= productService;
	}

//	//ok
//	@RequestMapping(value = "/listProduct", method = RequestMethod.GET)
//	public ResponseEntity<?> listAllProducts(){
//		List<ProductDTO> productList = productService.listAllProducts();
//		if(!productList.isEmpty())
//			return new ResponseEntity<>(productList, HttpStatus.OK);
//		else
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}

	//ok
		@RequestMapping(value = "/listAllProductIncludeImage", method = RequestMethod.GET)
		public ResponseEntity<?> listAllProductIncludeImage(){
			List<ProductIncludeImageDTO> productList= productService.listAllProductIncludeImage();
			if(!productList.isEmpty()) 
				return new ResponseEntity<>(productList, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		//ok
		@RequestMapping(value = "/getProductById/{id}", method = RequestMethod.GET)
		public ResponseEntity<ProductDetailDTO> getProductById(@PathVariable(name = "id") String id) {
			ProductDetailDTO productDetailDTO= productService.getProductByIdUser(id);
			if(productService.getProductById(id) != null) 
				return new ResponseEntity<ProductDetailDTO>(productDetailDTO, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
//		//ok
//		@RequestMapping(value = "/getProductAllById/{id}", method = RequestMethod.GET)
//		public ResponseEntity<ProductListDTO> getProductByIdAll(@PathVariable(name = "id") String id) {
//			if(productService.getProductById(id) != null) 
//				return new ResponseEntity<>(productService.getProductByIdAdmin(id), HttpStatus.OK);
//			else
//				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
}
