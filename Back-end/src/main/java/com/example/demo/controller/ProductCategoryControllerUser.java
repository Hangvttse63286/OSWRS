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
public class ProductCategoryControllerUser {

	@Autowired
	private ModelMapper modelMapper;

	private final CategoryService categoryService;

	public ProductCategoryControllerUser(CategoryService categoryService) {
		super();
		this.categoryService= categoryService;
	}

	@RequestMapping(value = "/listCategory", method = RequestMethod.GET)
	public ResponseEntity<?> listCategories() {
		if(categoryService.listAllCategories() != null)
			return new ResponseEntity<>(categoryService.listAllCategories(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/getProductByCategoryId/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProductByCategory(@PathVariable(name = "id") Long id) {
		if(categoryService.listProductByCategoryId(id) != null)
			return new ResponseEntity<>(categoryService.listProductByCategoryId(id), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
