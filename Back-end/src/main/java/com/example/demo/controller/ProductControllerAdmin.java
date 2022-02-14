package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.payload.ProductDTO;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;



@RestController
@RequestMapping("/api/product/admin/")
public class ProductControllerAdmin {

	@Autowired
	private ModelMapper modelMapper;
	
	private final ProductService productService;
	
	public ProductControllerAdmin(ProductService productService) {
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
	
	@RequestMapping(value = "/updateProductById/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ProductDTO> updateProductById(@PathVariable(name = "id") String id, @RequestBody ProductDTO productDTO) {
		Product productRequest= modelMapper.map(productDTO, Product.class);
		Product product= productService.updateProductById(id, productRequest);
		
		ProductDTO productResponseDto= modelMapper.map(product, ProductDTO.class);
		return ResponseEntity.ok().body(productResponseDto);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		Product productRequest= modelMapper.map(productDTO, Product.class);
		Product product= productService.createProduct(productRequest);
		
		ProductDTO productResponseDto= modelMapper.map(product, ProductDTO.class);
		return new ResponseEntity<ProductDTO>(productResponseDto, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/deleteProductById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteProductById(@PathVariable(name = "id") String id) {
		productService.deleteProduct(id);
		try {
			productService.deleteProduct(id);
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
}
