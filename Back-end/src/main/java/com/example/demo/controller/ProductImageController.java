package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ProductImageService;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/api/product/")
public class ProductImageController {
	
	private final ProductImageService productImageService;
	
	public ProductImageController(ProductImageService productImageService) {
		super();
		this.productImageService= productImageService;
	}
	
	@RequestMapping(value = "/listImage", method = RequestMethod.GET)
	public ResponseEntity<?> listImage(){
		if(productImageService.listImage() != null)
			return new ResponseEntity<>(productImageService.listImage(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
