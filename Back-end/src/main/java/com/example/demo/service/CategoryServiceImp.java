package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.common.ECategory;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product_Image;
import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.Product;
import com.example.demo.payload.AddressDto;
import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductImageDTO;
import com.example.demo.payload.ProductIncludeImageDTO;
import com.example.demo.payload.ProductDetailDTO;
import com.example.demo.payload.ProductSkuDTO;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSKURepository;

@Service
public class CategoryServiceImp implements CategoryService{

	private final ProductSKURepository productSKURepository;
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	public CategoryServiceImp(ProductRepository productRepository, CategoryRepository categoryRepository, ProductSKURepository productSKURepository) {
		super();
		this.productRepository= productRepository;
		this.categoryRepository= categoryRepository;
		this.productSKURepository= productSKURepository;
	}

	//Update Category [Admin] - ok
		@Override
		public CategoryDTO updateCategoryById(Long id, CategoryDTO categoryRequest) {
			Category category= categoryRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));

			category.setName(categoryRequest.getCategory_name());
			category.setIs_deleted(categoryRequest.isIs_deleted());
			categoryRepository.save(category);
			return getCategoryById(id);
		}

		@Override
		public CategoryDTO getCategoryById(Long id) {
			Category category= categoryRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
			CategoryDTO categoryDTO= new CategoryDTO();

			categoryDTO.setCategory_name(category.getName());
			categoryDTO.setIs_deleted(category.isIs_deleted());
			return categoryDTO;
		}


		//Delete Category - Admin
		@Override
		public void deleteCategory(Long id) {
			Category category= categoryRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
			Set<Product> products = category.getProducts();
			if (!products.isEmpty()) {
				for (Product product : products) {
					product.getCategories().remove(category);
				}
				productRepository.saveAllAndFlush(products);
			}

			categoryRepository.delete(category);
		}

		@Override
		public List<CategoryDTO> listAllCategories() {
			List<Category> resultOptional= categoryRepository.findAll();
			if(resultOptional.isEmpty()) {
				return new ArrayList<>();
			}
			List<CategoryDTO> categoryDTOs= new ArrayList<CategoryDTO>();


			for(Category c: resultOptional) {
				CategoryDTO categoryDTO= new CategoryDTO();
				categoryDTO.setId(c.getId());
				categoryDTO.setCategory_name(c.getName());
				categoryDTO.setIs_deleted(c.isIs_deleted());

				categoryDTOs.add(categoryDTO);
			}
			return categoryDTOs;
		}


		@Override
		public Category createCategory(CategoryDTO categoryRequest) {
			Category category= new Category();
			Category category2= categoryRepository.findByName(categoryRequest.getCategory_name());

			if(category2 == null) {
				category.setName(categoryRequest.getCategory_name());
				category.setIs_deleted(categoryRequest.isIs_deleted());
				return categoryRepository.save(category);
			}
			else
				return null;
		}

		@Override
		public List<ProductIncludeImageDTO> listProductByCategoryId(Long id) {
			Category category= categoryRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));

			List<ProductIncludeImageDTO> productList= new ArrayList<>();

			for(Product product: category.getProducts()) {

				ProductIncludeImageDTO productDTO= new ProductIncludeImageDTO();
				productDTO.setProduct_id(product.getProduct_id());
				productDTO.setProduct_status_id(product.getProduct_status_id());
				productDTO.setProduct_name(product.getProduct_name());
				productDTO.setSearch_word(product.getSearch_word());
				productDTO.setPrice(product.getPrice());
				for(Product_Image p: product.getProduct_Image()) {
					if(p.isPrimary() == true) {
						productDTO.setImageUrl(p.getUrl());
					}
				}
				productList.add(productDTO);
			}
			return productList;
		}

		@Override
		public List<ProductIncludeImageDTO> listProductByCategoryName(String name) {
			Category category= categoryRepository.findByName(name);
			Set<Product> products = category.getProducts();
			if (products.isEmpty())
				return new ArrayList<ProductIncludeImageDTO>();
			List<ProductIncludeImageDTO> productList= new ArrayList<>();

			for(Product product: category.getProducts()) {
				ProductIncludeImageDTO productDTO= new ProductIncludeImageDTO();
				productDTO.setProduct_id(product.getProduct_id());
				productDTO.setProduct_status_id(product.getProduct_status_id());
				productDTO.setProduct_name(product.getProduct_name());
				productDTO.setSearch_word(product.getSearch_word());
				productDTO.setPrice(product.getPrice());
				for(Product_Image p: product.getProduct_Image()) {
					if(p.isPrimary() == true) {
						productDTO.setImageUrl(p.getUrl());
					}
				}
				productList.add(productDTO);
			}
			return productList;
		}
}
