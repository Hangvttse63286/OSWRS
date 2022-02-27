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

import com.example.demo.entity.Category;
import com.example.demo.entity.Products;
import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductDTO;
import com.example.demo.payload.ProductSkuDTO;
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
	}
	
	
	@RequestMapping(value = "/getProductById/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") String id) {
		Products products= productService.getProductById(id);
		ProductDTO productResponseDto= modelMapper.map(products, ProductDTO.class);
		
		return ResponseEntity.ok().body(productResponseDto);
	}
	
	@RequestMapping(value = "/listCategory", method = RequestMethod.GET)
	public List<CategoryDTO> listCategories() {
		return productService.listAllCategories().stream().map(categories -> modelMapper.map(categories , CategoryDTO.class)).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/getProductByCategoryId/{id}", method = RequestMethod.GET)
	public List<ProductDTO> getProductByCategory(@PathVariable(name = "id") Long id) {
		return productService.listProductByCategoryId(id).stream().map(products -> modelMapper.map(products , ProductDTO.class)).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/listProduct_SKU", method = RequestMethod.GET)
	public List<ProductSkuDTO> listProductSKU() {
		return productService.listAllProductSKU().stream().map(product_sku -> modelMapper.map(product_sku , ProductSkuDTO.class)).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/getProductBySKUId/{id}", method = RequestMethod.GET)
	public List<ProductDTO> getProductBySKUId(@PathVariable(name = "id") Long id) {
		return productService.listProductBySKUId(id).stream().map(products -> modelMapper.map(products , ProductDTO.class)).collect(Collectors.toList());
	}
}
