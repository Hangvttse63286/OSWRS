package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.example.demo.entity.Category;
import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductIncludeImageDTO;
import com.example.demo.payload.ProductDetailDTO;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private ModelMapper modelMapper;

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService= categoryService;
	}

	@RequestMapping(value = "/listCategory", method = RequestMethod.GET)
	public ResponseEntity<?> listCategories() {
		List<CategoryDTO> categoryList = categoryService.listAllCategories();
		if(!categoryList.isEmpty())
			return new ResponseEntity<>(categoryList, HttpStatus.OK);
		else
			return new ResponseEntity<>("Error: No category found.", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/getProductByCategoryId/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProductByCategoryId(@PathVariable(name = "id") Long id) {
		try {
			List<ProductIncludeImageDTO> pList = categoryService.listProductByCategoryId(id);
			if (!pList.isEmpty())
				return new ResponseEntity<>(pList, HttpStatus.OK);
			else
				return new ResponseEntity<>("Error: No product found.", HttpStatus.NOT_FOUND);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

//	@RequestMapping(value = "/getProductByCategoryName/{name}", method = RequestMethod.GET)
//	public ResponseEntity<?> getProductByCategoryName(@PathVariable(name = "name") String name) {
//		List<ProductIncludeImageDTO> pList= categoryService.listProductByCategoryName(name);
//		if(!pList.isEmpty())
//			return new ResponseEntity<>(pList, HttpStatus.OK);
//		else
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}

//	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/admin/updateCategoryById/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCategoryById(@PathVariable(name = "id") Long id, @RequestBody CategoryDTO categoryDTO) {
		try {
			if (!categoryService.getCategoryById(id).getCategory_name().equalsIgnoreCase(categoryDTO.getCategory_name()))
				if (categoryService.existsByName(categoryDTO.getCategory_name()))
					return new ResponseEntity<>("Error: Category has already existed", HttpStatus.CONFLICT);

			CategoryDTO category = categoryService.updateCategoryById(id, categoryDTO);
			return new ResponseEntity<>(category, HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

//	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/admin/createCategory", method = RequestMethod.POST)
	public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO result = categoryService.createCategory(categoryDTO);
		if(result == null) {
			return new ResponseEntity<>("Error: Category has already existed", HttpStatus.CONFLICT);
		}
		else {
			return new ResponseEntity<>(HttpStatus.OK);
		}

	}

//	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/admin/deleteCatgoryById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCatgoryById(@PathVariable(name = "id") Long id) {
		try {
			categoryService.deleteCategory(id);
			return new ResponseEntity<>("Delete successfully!", HttpStatus.OK);
		}
		catch (NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
