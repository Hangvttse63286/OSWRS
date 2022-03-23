package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.ProductIncludeSkuDTO;
import com.example.demo.payload.ProductListDTO;
import com.example.demo.service.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductControllerUser {

	private final ProductService productService;


	public ProductControllerUser(ProductService productService) {
		super();
		this.productService= productService;
	}

	//ok
	@RequestMapping(value = "/listProduct", method = RequestMethod.GET)
	public ResponseEntity<?> listAllProducts(){
		if(!productService.listAllProducts().isEmpty())
			return new ResponseEntity<>(productService.listAllProducts(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	//ok
	@RequestMapping(value = "/listAllProductIncludeImage", method = RequestMethod.GET)
	public ResponseEntity<?> listAllProductIncludeImage(){
		if(!productService.listAllProducts().isEmpty())
			return new ResponseEntity<>(productService.listAllProductIncludeImage(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	//ok
	@RequestMapping(value = "/getProductById/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductIncludeSkuDTO> getProductById(@PathVariable(name = "id") Long id) {
		if(productService.getProductById(id) != null)
			return new ResponseEntity<ProductIncludeSkuDTO>(productService.getProductByIdUser(id), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	//ok
	@RequestMapping(value = "/getProductAllById/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductListDTO> getProductByIdAll(@PathVariable(name = "id") Long id) {
		if(productService.getProductById(id) != null)
			return new ResponseEntity<>(productService.getProductByIdAdmin(id), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}


}
