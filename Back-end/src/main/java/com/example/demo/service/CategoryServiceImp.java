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
import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductImageDTO;
import com.example.demo.payload.ProductIncludeSkuDTO;
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

		category.setCategory_name(categoryRequest.getCategory_name());
		category.setIs_deleted(categoryRequest.isIs_deleted());
		categoryRepository.save(category);
		return getCategoryById(id);
	}

	@Override
	public CategoryDTO getCategoryById(Long id) {
		Category category= categoryRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		CategoryDTO categoryDTO= new CategoryDTO();

		categoryDTO.setCategory_name(category.getCategory_name());
		categoryDTO.setIs_deleted(category.isIs_deleted());
		return categoryDTO;
	}
	//Delete Category - Admin
	@Override
	public void deleteCategory(Long id) {
		Category category= categoryRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		categoryRepository.delete(category);
	}

	@Override
	public List<CategoryDTO> listAllCategories() {
		List<Category> resultOptional= categoryRepository.findAll();
		if(resultOptional.isEmpty()) {
			throw new NullPointerException("Error: No object found.");
		}
		List<CategoryDTO> categoryDTOs= new ArrayList<CategoryDTO>();


		for(Category c: resultOptional) {
			CategoryDTO categoryDTO= new CategoryDTO();
			categoryDTO.setId(c.getId());
			categoryDTO.setCategory_name(c.getCategory_name());
			categoryDTO.setIs_deleted(c.isIs_deleted());

			categoryDTOs.add(categoryDTO);
		}
		return categoryDTOs;
	}


	@Override
	public List<ProductIncludeSkuDTO> listProductByCategoryId(Long id) {

		List<Product> resultOptional= productRepository.findByCategoriesId(id);
		if(resultOptional.isEmpty()) {
			throw new NullPointerException("Error: No object found.");
		}


		List<ProductIncludeSkuDTO> productDTOList= new ArrayList<ProductIncludeSkuDTO>();


		List<ProductSkuDTO> productSkuDTOList= new ArrayList<ProductSkuDTO>();

		for(Product product: resultOptional) {
			List<ProductImageDTO> pList= new ArrayList<ProductImageDTO>();
			ProductIncludeSkuDTO productIncludeSkuDTO= new ProductIncludeSkuDTO();
			for(Product_Image p: product.getProduct_Image()) {
				ProductImageDTO pDto= new ProductImageDTO();
				if(p.isPrimary() == true) {
					pDto.setName(p.getName());
					pDto.setProduct_image_id(p.getProduct_image_id());
					pDto.setUrl(p.getUrl());
					pList.add(pDto);
				}
			}
			productIncludeSkuDTO.setProductImage(pList);
			productIncludeSkuDTO.setProduct_id(product.getId());
			productIncludeSkuDTO.setProduct_status_id(product.getProduct_status_id());
			productIncludeSkuDTO.setProduct_name(product.getProduct_name());
			productIncludeSkuDTO.setDescription_list(product.getDescription_list());
			productIncludeSkuDTO.setDescription_details(product.getDescription_details());
			productIncludeSkuDTO.setSearch_word(product.getSearch_word());
			productIncludeSkuDTO.setPrice(product.getPrice());
			for(Product_SKU pSKU: product.getProductSKUs()) {
				ProductSkuDTO productSkuDTO= new ProductSkuDTO();
				productSkuDTO.setId(pSKU.getId());
				productSkuDTO.setStock(pSKU.getStock());
				productSkuDTO.setSale_limit(pSKU.getSale_limit());
				productSkuDTO.setSize(pSKU.getSize());
				productSkuDTO.setIs_deleted(pSKU.isIs_deleted());

				productSkuDTOList.add(productSkuDTO);
			}
			productIncludeSkuDTO.setProductSKUs(productSkuDTOList);
			productDTOList.add(productIncludeSkuDTO);
		}
		return productDTOList;

	}

	@Override
	public Category createCategory(CategoryDTO categoryRequest) {
		Category category= new Category();
		Category category2= categoryRepository.findByName(categoryRequest.getCategory_name());

		if(category2 == null) {
			category.setCategory_name(categoryRequest.getCategory_name());
			category.setIs_deleted(categoryRequest.isIs_deleted());
			return categoryRepository.save(category);
		}
		else
			return null;
	}



}
