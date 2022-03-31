package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductIncludeSkuDTO;


public interface CategoryService {
	//Category
	List<CategoryDTO> listAllCategories();
	List<ProductIncludeSkuDTO> listProductByCategory(String name);
	void deleteCategory(Long id);
	CategoryDTO getCategoryById(Long id);
	CategoryDTO updateCategoryById(Long id, CategoryDTO categoryRequest);
	CategoryDTO createCategory(CategoryDTO categoryRequest);
}
