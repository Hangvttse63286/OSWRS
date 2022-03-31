package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductIncludeSkuDTO;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/api/category")
public class CategoryControllerUser {

	@Autowired
	private ModelMapper modelMapper;

	private final CategoryService categoryService;

	public CategoryControllerUser(CategoryService categoryService) {
		super();
		this.categoryService= categoryService;
	}

	@RequestMapping(value = "/listCategory", method = RequestMethod.GET)
	public ResponseEntity<?> listCategories() {
		List<CategoryDTO> categoryList = categoryService.listAllCategories();
		if(categoryList != null)
			return new ResponseEntity<>(categoryList, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/getProductByCategoryName/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getProductByCategory(@PathVariable(name = "name") String name) {
		List<ProductIncludeSkuDTO> productList = categoryService.listProductByCategory(name);
		if(productList != null)
			return new ResponseEntity<>(productList, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
