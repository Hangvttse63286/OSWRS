package com.example.demo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductIncludeSkuDTO;
import com.example.demo.service.CategoryService;


@RestController
@RequestMapping("/api/admin/category")
public class ProductCategoryControllerAdmin {
	@Autowired
	private ModelMapper modelMapper;

	private final CategoryService categoryService;

	public ProductCategoryControllerAdmin(CategoryService categoryService) {
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

	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	@RequestMapping(value = "/updateCategoryById/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CategoryDTO> updateCategoryById(@PathVariable(name = "id") Long id, @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO category = categoryService.updateCategoryById(id, categoryDTO);
		if(category != null) {
			return new ResponseEntity<>(category, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	@RequestMapping(value = "/createCategory", method = RequestMethod.POST)
	public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO result = categoryService.createCategory(categoryDTO);
		if(result == null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		else {
			return new ResponseEntity<>(HttpStatus.OK);
		}

	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	@RequestMapping(value = "/deleteCatgoryById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCatgoryById(@PathVariable(name = "id") Long id) {
		if(categoryService.getCategoryById(id) != null) {
			categoryService.deleteCategory(id);
			return new ResponseEntity<>("Delete successfully!", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
