package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.payload.ProductDTO;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/api/product/user/")
public class ProductControllerUser {

	@Autowired
	private ModelMapper modelMapper;
	
	private final ProductService productService;
	
	public ProductControllerUser(ProductService productService) {
		super();
		this.productService= productService;
	}
	
	@RequestMapping(value = "/listProduct", method = RequestMethod.GET)
	public List<ProductDTO> listAllProducts(){
		return productService.listAllProducts().stream().map(product -> modelMapper.map(product , ProductDTO.class)).collect(Collectors.toList());
//		return productRepository.findAll();
	}
	
	@RequestMapping(value = "/getProductById/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") String id) {
		Product product= productService.getProductById(id);
		ProductDTO productResponseDto= modelMapper.map(product, ProductDTO.class);
		
		return ResponseEntity.ok().body(productResponseDto);
	}
}
