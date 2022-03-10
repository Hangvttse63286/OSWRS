package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.common.ECategory;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product_Image;
import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.Products;
import com.example.demo.payload.ProductIncludeSkuDTO;
import com.example.demo.payload.ProductListDTO;
import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductDTO;
import com.example.demo.payload.ProductImageDTO;
import com.example.demo.payload.ProductIncludeImageDTO;
import com.example.demo.payload.ProductSkuDTO;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSKURepository;

import javassist.NotFoundException;

@Service
public class ProductServiceImp implements ProductService{

	private final ProductSKURepository productSKURepository;
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	
	public ProductServiceImp(ProductRepository productRepository, CategoryRepository categoryRepository, ProductSKURepository productSKURepository) {
		super();
		this.productRepository= productRepository;
		this.categoryRepository= categoryRepository;
		this.productSKURepository= productSKURepository;
	}

	//List Product [User] ->ok
	@Override
	public List<ProductDTO> listAllProducts() {
		if(productRepository.findAll().isEmpty())
			throw new NullPointerException("Error: No object found.");
		
		List<ProductDTO> productDTOList= new ArrayList<>();

		
		for(Products product: productRepository.findAll()) {
			ProductDTO productDTO= new ProductDTO();
			productDTO.setProduct_id(product.getProduct_id());
			productDTO.setProduct_status_id(product.getProduct_status_id());
			productDTO.setProduct_name(product.getProduct_name());
			productDTO.setDescription_list(product.getDescription_list());
			productDTO.setDescription_details(product.getDescription_details());
			productDTO.setSearch_word(product.getSearch_word());
			productDTO.setDiscount_id(product.getDiscount_id());
			
			productDTOList.add(productDTO);
		}
		return productDTOList;
	}
	
	// get product by id [user]->ok
	@Override
	public ProductIncludeSkuDTO getProductByIdUser(String id) {
		Products products = productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		
		ProductIncludeSkuDTO productIncludeSkuDTO= new ProductIncludeSkuDTO();
		List<ProductIncludeSkuDTO> productDTOList= new ArrayList<ProductIncludeSkuDTO>();

		List<ProductSkuDTO> productSkuDTOList= new ArrayList<ProductSkuDTO>();

		productIncludeSkuDTO.setProduct_id(products.getProduct_id());
		productIncludeSkuDTO.setProduct_status_id(products.getProduct_status_id());
		productIncludeSkuDTO.setProduct_name(products.getProduct_name());
		productIncludeSkuDTO.setDescription_list(products.getDescription_list());
		productIncludeSkuDTO.setDescription_details(products.getDescription_details());
		productIncludeSkuDTO.setSearch_word(products.getSearch_word());
		productIncludeSkuDTO.setDiscount_id(products.getDiscount_id());
		for(Product_SKU pSKU: products.getProductSKUs()) {		
			ProductSkuDTO productSkuDTO= new ProductSkuDTO();
			productSkuDTO.setId(pSKU.getId());
			productSkuDTO.setStock(pSKU.getStock());
			productSkuDTO.setSale_limit(pSKU.getSale_limit());
			productSkuDTO.setSize(pSKU.getSize());
			productSkuDTO.setPrice(pSKU.getPrice());
			productSkuDTO.setIs_deleted(pSKU.isIs_deleted());
			
			productSkuDTOList.add(productSkuDTO);
		}
		productIncludeSkuDTO.setProductSKUs(productSkuDTOList);
		return productIncludeSkuDTO;
	}
	
	//Create product - Admin
//	@Override
//	public ProductIncludeImageDTO createProduct(ProductIncludeImageDTO productRequest) {
//		
//		Set<Product_Image> productImageDTOList= new HashSet<>();
//		Product_Image productImage= new Product_Image();
//		Products products= new Products();
//		
//		products.setProduct_id(productRequest.getProduct_id());
//		products.setProduct_status_id(productRequest.getProduct_status_id());
//		products.setProduct_name(productRequest.getProduct_name());
//		products.setDescription_list(productRequest.getDescription_list());
//		products.setDescription_details(productRequest.getDescription_details());
//		products.setSearch_word(productRequest.getSearch_word());
//		products.setDiscount_id(productRequest.getDiscount_id());
//		for(ProductImageDTO p: productRequest.getProductImage()) {
//			productImage.setName(p.getName());
//			productImage.setProduct_image_id(p.getProduct_image_id());
//			productImage.setUrl(p.getUrl());
//			productImageDTOList.add(productImage);
//		}
//		products.setProduct_Image(productImageDTOList);
//		
//		
//		return productRepository.save(products);
//		
//	}
	
	//Create product(category+sku) - Admin
		@Override
		public Products createProductAll(ProductListDTO productRequest) {
			 
			Set<Product_Image> productImageDTOList= new HashSet<>();
			Product_Image productImage= new Product_Image();
			
			Set<Category> categoryList= new HashSet<Category>();
			Category cate= new Category();
			
			Category category= categoryRepository.findByName(productRequest.getCategory().get(0).getCategory_name());
			
			List<Product_SKU> product_SKU_List= new ArrayList<Product_SKU>();
			Product_SKU product_SKU= new Product_SKU();
			
			Products products= new Products();

			if(category == null) {
				for(CategoryDTO c: productRequest.getCategory()) {
					cate.setCategory_name(c.getCategory_name());
					cate.setIs_deleted(c.isIs_deleted());
					categoryList.add(cate);
				}
			}		
			products.setCategories(categoryList);				
			for(ProductImageDTO p: productRequest.getProductImage()) {
				productImage.setName(p.getName());
				productImage.setUrl("/product-images/" + products.getProduct_id() + p.getProduct_image_id());
				productImageDTOList.add(productImage);
			}
			products.setProduct_Image(productImageDTOList);
			products.setProduct_id(productRequest.getProduct_id());
			products.setProduct_status_id(productRequest.getProduct_status_id());
			products.setProduct_name(productRequest.getProduct_name());
			products.setDescription_list(productRequest.getDescription_list());
			products.setDescription_details(productRequest.getDescription_details());
			products.setSearch_word(productRequest.getSearch_word());
			products.setDiscount_id(productRequest.getDiscount_id());
			for(ProductSkuDTO p: productRequest.getProductSKUs()) {
				product_SKU.setId(p.getId());
				product_SKU.setStock(p.getStock());
				product_SKU.setSale_limit(p.getSale_limit());
				product_SKU.setSize(p.getSize());
				product_SKU.setPrice(p.getPrice());
				product_SKU.setIs_deleted(p.isIs_deleted());
				product_SKU_List.add(product_SKU);
			}
			products.setProductSKUs((Set<Product_SKU>) product_SKU_List);
			
			
			return productRepository.save(products);		
		}

		@Override
		public Products createProduct(ProductDTO productRequest) {
			List<Products> productList= productRepository.findAll();
			Product_SKU product_SKU= new Product_SKU();
			
			Products products= new Products();			

			for(Products p: productList) {
				if(productRequest.getProduct_id().equalsIgnoreCase(p.getProduct_id())) {
					return null;
				}
				else {
					products.setProduct_id(productRequest.getProduct_id());
					products.setProduct_status_id(productRequest.getProduct_status_id());
					products.setProduct_name(productRequest.getProduct_name());
					products.setDescription_list(productRequest.getDescription_list());
					products.setDescription_details(productRequest.getDescription_details());
					products.setSearch_word(productRequest.getSearch_word());
					products.setDiscount_id(productRequest.getDiscount_id());
				}
			}
			
			return productRepository.save(products);		
		}
		
	// Get product by id [Admin]->ok
	@Override
	public ProductListDTO getProductByIdAdmin(String id) {
		Products products = productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		
		ProductListDTO ProductListDTO= new ProductListDTO();
		List<ProductListDTO> productDTOList= new ArrayList<ProductListDTO>();

		List<ProductSkuDTO> productSkuDTOList= new ArrayList<ProductSkuDTO>();
		List<CategoryDTO> categoryDTOList= new ArrayList<CategoryDTO>();

		CategoryDTO categoryDTO= new CategoryDTO();
		for(Category c: products.getCategories()) {
			categoryDTO.setId(c.getId());
			categoryDTO.setCategory_name(c.getCategory_name());
			categoryDTO.setIs_deleted(c.isIs_deleted());

		}
		categoryDTOList.add(categoryDTO);
		ProductListDTO.setCategory(categoryDTOList);
		ProductListDTO.setProduct_id(products.getProduct_id());
		ProductListDTO.setProduct_status_id(products.getProduct_status_id());
		ProductListDTO.setProduct_name(products.getProduct_name());
		ProductListDTO.setDescription_list(products.getDescription_list());
		ProductListDTO.setDescription_details(products.getDescription_details());
		ProductListDTO.setSearch_word(products.getSearch_word());
		ProductListDTO.setDiscount_id(products.getDiscount_id());
		for(Product_SKU pSKU: products.getProductSKUs()) {		
			ProductSkuDTO productSkuDTO= new ProductSkuDTO();
			productSkuDTO.setId(pSKU.getId());
			productSkuDTO.setStock(pSKU.getStock());
			productSkuDTO.setSale_limit(pSKU.getSale_limit());
			productSkuDTO.setSize(pSKU.getSize());
			productSkuDTO.setPrice(pSKU.getPrice());
			productSkuDTO.setIs_deleted(pSKU.isIs_deleted());
			
			productSkuDTOList.add(productSkuDTO);
		}
		ProductListDTO.setProductSKUs(productSkuDTOList);
		return ProductListDTO;
	}
		
	//Delete PRoduct [Admin] - ok
	@Override
	public void deleteProduct(String id) {
		Products products= productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		productRepository.delete(products);
	}

	//Update Product [Admin] - ok
	@Override
	public ProductDTO updateProductById(String id, ProductDTO productRequest) {
		Products products= productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
			
		products.setDescription_details(productRequest.getDescription_details());
		products.setDescription_list(productRequest.getDescription_list());
		products.setDiscount_id(productRequest.getDiscount_id());
		products.setProduct_name(productRequest.getProduct_name());
		products.setProduct_status_id(productRequest.getProduct_status_id());
		products.setSearch_word(productRequest.getSearch_word());
		productRepository.save(products);
		return getProductById(id);
	}
	
	//ok
	@Override
	public ProductDTO getProductById(String id) {
		Products products = productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		
		ProductDTO productDTO= new ProductDTO();

		productDTO.setProduct_id(products.getProduct_id());
		productDTO.setProduct_status_id(products.getProduct_status_id());
		productDTO.setProduct_name(products.getProduct_name());
		productDTO.setDescription_list(products.getDescription_list());
		productDTO.setDescription_details(products.getDescription_details());
		productDTO.setSearch_word(products.getSearch_word());
		productDTO.setDiscount_id(products.getDiscount_id());	
		
		return productDTO;
	}

	
	//Delete Product-sku - Admin
	@Override
	public void deleteProductSku(Long id) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		productSKURepository.delete(product_SKU);
	}


	@Override
	public List<Products> listProductBySKUId(Long id) {
		List<Products> resultOptional= productRepository.findByProductSKUsId(id); 
		if(resultOptional.isEmpty()) {
			throw new NullPointerException("Error: No object found.");
		}
		else {
			return resultOptional;
		}
	}

	@Override
	public List<ProductIncludeImageDTO> listAllProductIncludeImage() {
		List<Products> products= productRepository.findAll();
		if(products.isEmpty())
			throw new NullPointerException("Error: No object found.");
		
		List<ProductIncludeImageDTO> productList= new ArrayList<>();

				
		for(Products product: productRepository.findAll()) {

			List<ProductImageDTO> pList= new ArrayList<ProductImageDTO>();
			ProductIncludeImageDTO productDTO= new ProductIncludeImageDTO();
			productDTO.setProduct_id(product.getProduct_id());
			productDTO.setProduct_status_id(product.getProduct_status_id());
			productDTO.setProduct_name(product.getProduct_name());
			productDTO.setDescription_list(product.getDescription_list());
			productDTO.setDescription_details(product.getDescription_details());
			productDTO.setSearch_word(product.getSearch_word());
			productDTO.setDiscount_id(product.getDiscount_id());
			
			for(Product_Image p: product.getProduct_Image()) {
				ProductImageDTO pDto= new ProductImageDTO();
				pDto.setName(p.getName());
				pDto.setProduct_image_id(p.getProduct_image_id());
				pDto.setUrl(p.getUrl());
				pList.add(pDto);
			}
			productDTO.setProductImage(pList);
			productList.add(productDTO);
		}
		return productList;
	}






	
}
