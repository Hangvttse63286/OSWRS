package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductIncludeSkuDTO;
import com.example.demo.payload.ProductSkuDTO;
import com.example.demo.repository.ProductSKURepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductSKUService;
import com.example.demo.service.ProductSKUServiceImpl;

@RestController
@RequestMapping("/api/productSKU/user/")
public class ProductSKUControllerUser {
	private final ProductSKUService productSKUService;
	
	public ProductSKUControllerUser(ProductSKUService productSKUService) {
		super();
		this.productSKUService= productSKUService;
	}
	
	@RequestMapping(value = "/listProduct_SKU", method = RequestMethod.GET)
	public ResponseEntity<?> listProductSKU() {
		if(productSKUService.listAllProductSku() != null)
			return new ResponseEntity<>(productSKUService.listAllProductSku(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/getProductBySKUId/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProductBySKUId(@PathVariable(name = "id") Long id) {
		if(productSKUService.getProductSkuById(id) != null)
			return new ResponseEntity<>(productSKUService.getProductSkuById(id), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
}
