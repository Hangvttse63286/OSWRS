package com.example.demo.controller;

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
import com.example.demo.service.CategoryService;


@RestController
@RequestMapping("/api/category/admin/")
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
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	@RequestMapping(value = "/updateCategoryById/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CategoryDTO> updateCategoryById(@PathVariable(name = "id") Long id, @RequestBody CategoryDTO categoryDTO) {
		if(categoryService.getCategoryById(id) != null) {
			return new ResponseEntity<>(categoryService.updateCategoryById(id, categoryDTO), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	@RequestMapping(value = "/createCategory", method = RequestMethod.POST)
	public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
		if(categoryService.createCategory(categoryDTO) == null) {
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
