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
import com.example.demo.entity.Product;
import com.example.demo.payload.ProductIncludeSkuDTO;
import com.example.demo.payload.ProductListDTO;
import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductCreateDTO;
import com.example.demo.payload.ProductDTO;
import com.example.demo.payload.ProductImageDTO;
import com.example.demo.payload.ProductIncludeImageDTO;
import com.example.demo.payload.ProductSkuDTO;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductImageRepository;
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
		List<Product> productList = productRepository.findAll();
		if(productList.isEmpty())
			throw new NullPointerException("Error: No object found.");

		List<ProductDTO> productDTOList= new ArrayList<>();


		for(Product product: productList) {
			ProductDTO productDTO= new ProductDTO();
			productDTO.setProduct_id(product.getProduct_id());
			productDTO.setProduct_status_id(product.getProduct_status_id());
			productDTO.setProduct_name(product.getProduct_name());
//			productDTO.setDescription_list(product.getDescription_list());
//			productDTO.setDescription_details(product.getDescription_details());
			productDTO.setSearch_word(product.getSearch_word());
			productDTO.setPrice(product.getPrice());
			productDTOList.add(productDTO);
		}
		return productDTOList;
	}


	// get product by id [user]->ok
	@Override
	public ProductIncludeSkuDTO getProductByIdUser(String id) {
		Product products = productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));

		ProductIncludeSkuDTO productIncludeSkuDTO= new ProductIncludeSkuDTO();
		List<ProductIncludeSkuDTO> productDTOList= new ArrayList<ProductIncludeSkuDTO>();

		List<ProductSkuDTO> productSkuDTOList= new ArrayList<ProductSkuDTO>();
		List<ProductImageDTO> pList= new ArrayList<ProductImageDTO>();
		for(Product_Image p: products.getProduct_Image()) {
			ProductImageDTO pDto= new ProductImageDTO();
			pDto.setName(p.getName());
			pDto.setProduct_image_id(p.getProduct_image_id());
			pDto.setUrl(p.getUrl());
			pList.add(pDto);
		}
		productIncludeSkuDTO.setProductImage(pList);
		productIncludeSkuDTO.setProduct_id(products.getProduct_id());
		productIncludeSkuDTO.setProduct_status_id(products.getProduct_status_id());
		productIncludeSkuDTO.setProduct_name(products.getProduct_name());
		productIncludeSkuDTO.setDescription_list(products.getDescription_list());
		productIncludeSkuDTO.setDescription_details(products.getDescription_details());
		productIncludeSkuDTO.setSearch_word(products.getSearch_word());
		productIncludeSkuDTO.setPrice(products.getPrice());
		for(Product_SKU pSKU: products.getProductSKUs()) {
			ProductSkuDTO productSkuDTO= new ProductSkuDTO();
			productSkuDTO.setId(pSKU.getId());
			productSkuDTO.setStock(pSKU.getStock());
			productSkuDTO.setSale_limit(pSKU.getSale_limit());
			productSkuDTO.setSize(pSKU.getSize());
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
//		Product products= new Product();
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

	//Create product(category+image) - Admin
	@Override
	public Product createProductAll(ProductCreateDTO productRequest) {
		Set<Product_Image> productImageList= new HashSet<>();

		Set<Category> categoryList= new HashSet<Category>();
		Category cate= new Category();

		Category category= categoryRepository.findByName(productRequest.getCategory().get(0).getCategory_name());

		List<Product_SKU> product_SKU_List= new ArrayList<Product_SKU>();
		Product_SKU product_SKU= new Product_SKU();

		Product products= new Product();

		if(category == null) {
			for(CategoryDTO c: productRequest.getCategory()) {
				cate.setName(c.getCategory_name());
				cate.setIs_deleted(c.isIs_deleted());
				categoryList.add(cate);
			}
		}
		else {
			categoryList.add(category);
		}
		products.setCategories(categoryList);
		for(ProductImageDTO p: productRequest.getProductImage()) {
			Product_Image productImage= new Product_Image();
			productImage.setName(p.getName());
			productImage.setUrl(p.getUrl());
			productImage.setProducts(productRepository.findById(productRequest.getProduct_id()).get());
			productImageList.add(productImage);
		}
		products.setProduct_Image(productImageList);
		products.setProduct_id(productRequest.getProduct_id());
		products.setProduct_status_id(productRequest.getProduct_status_id());
		products.setProduct_name(productRequest.getProduct_name());
		products.setDescription_list(productRequest.getDescription_list());
		products.setDescription_details(productRequest.getDescription_details());
		products.setSearch_word(productRequest.getSearch_word());
		products.setPrice(productRequest.getPrice());
//		for(ProductSkuDTO p: productRequest.getProductSKUs()) {
//			product_SKU.setId(p.getId());
//			product_SKU.setStock(p.getStock());
//			product_SKU.setSale_limit(p.getSale_limit());
//			product_SKU.setSize(p.getSize());
//			product_SKU.setIs_deleted(p.isIs_deleted());
//			product_SKU_List.add(product_SKU);
//		}
//
//		Set<Product_SKU> product_SKUs = new HashSet<Product_SKU>();
//
//		product_SKU_List.forEach(p -> { product_SKUs.add(p); });
//
//		products.setProductSKUs(product_SKUs);

		return productRepository.save(products);
	}

		@Override
		public Product createProduct(ProductDTO productRequest) {
			List<Product> productList= productRepository.findAll();
			Product_SKU product_SKU= new Product_SKU();

			Product products= new Product();

			for(Product p: productList) {
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
				}
			}

			return productRepository.save(products);
		}

	// Get product by id [Admin]->ok
	@Override
	public ProductListDTO getProductByIdAdmin(String id) {
		Product products = productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));

		ProductListDTO ProductListDTO= new ProductListDTO();
		List<ProductListDTO> productDTOList= new ArrayList<ProductListDTO>();

		List<ProductSkuDTO> productSkuDTOList= new ArrayList<ProductSkuDTO>();
		List<CategoryDTO> categoryDTOList= new ArrayList<CategoryDTO>();

		List<ProductImageDTO> pList= new ArrayList<ProductImageDTO>();

		CategoryDTO categoryDTO= new CategoryDTO();
		for(Category c: products.getCategories()) {
			categoryDTO.setId(c.getId());
			categoryDTO.setCategory_name(c.getName());
			categoryDTO.setIs_deleted(c.isIs_deleted());
			categoryDTOList.add(categoryDTO);
		}
		ProductListDTO.setCategory(categoryDTOList);
		for(Product_Image p: products.getProduct_Image()) {
			ProductImageDTO pDto= new ProductImageDTO();
			pDto.setName(p.getName());
			pDto.setProduct_image_id(p.getProduct_image_id());
			pDto.setUrl(p.getUrl());
			pList.add(pDto);
		}
		ProductListDTO.setProductImage(pList);
		ProductListDTO.setProduct_id(products.getProduct_id());
		ProductListDTO.setProduct_status_id(products.getProduct_status_id());
		ProductListDTO.setProduct_name(products.getProduct_name());
		ProductListDTO.setDescription_list(products.getDescription_list());
		ProductListDTO.setDescription_details(products.getDescription_details());
		ProductListDTO.setSearch_word(products.getSearch_word());
		ProductListDTO.setPrice(products.getPrice());
		for(Product_SKU pSKU: products.getProductSKUs()) {
			ProductSkuDTO productSkuDTO= new ProductSkuDTO();
			productSkuDTO.setId(pSKU.getId());
			productSkuDTO.setStock(pSKU.getStock());
			productSkuDTO.setSale_limit(pSKU.getSale_limit());
			productSkuDTO.setSize(pSKU.getSize());
			productSkuDTO.setIs_deleted(pSKU.isIs_deleted());
			productSkuDTOList.add(productSkuDTO);
		}
		ProductListDTO.setProductSKUs(productSkuDTOList);
		return ProductListDTO;
	}

	//Delete PRoduct [Admin] - ok
	@Override
	public void deleteProduct(String id) {
		Product products= productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		productRepository.delete(products);
	}

	//Update Product [Admin] - ok
	@Override
	public ProductDTO updateProductById(String id, ProductDTO productRequest) {
		Product products= productRepository.findById(id).get();
		if(products == null)
			return null;

		products.setDescription_details(productRequest.getDescription_details());
		products.setDescription_list(productRequest.getDescription_list());
		products.setProduct_name(productRequest.getProduct_name());
		products.setProduct_status_id(productRequest.getProduct_status_id());
		products.setSearch_word(productRequest.getSearch_word());
		productRepository.save(products);
		return getProductById(id);
	}

	//ok
	@Override
	public ProductDTO getProductById(String id) {
		Product products = productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));

		ProductDTO productDTO= new ProductDTO();

		productDTO.setProduct_id(products.getProduct_id());
		productDTO.setProduct_status_id(products.getProduct_status_id());
		productDTO.setProduct_name(products.getProduct_name());
		productDTO.setDescription_list(products.getDescription_list());
		productDTO.setDescription_details(products.getDescription_details());
		productDTO.setSearch_word(products.getSearch_word());

		return productDTO;
	}


	//Delete Product-sku - Admin
	@Override
	public void deleteProductSku(Long id) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		productSKURepository.delete(product_SKU);
	}


//	@Override
//	public List<Product> listProductBySKUId(Long id) {
//		List<Product> resultOptional= productRepository.findByProductSKUsId(id);
//		if(resultOptional.isEmpty()) {
//			throw new NullPointerException("Error: No object found.");
//		}
//		else {
//			return resultOptional;
//		}
//	}

	@Override
	public List<ProductIncludeImageDTO> listAllProductIncludeImage() {
		List<Product> products= productRepository.findAll();
		if(products.isEmpty())
			throw new NullPointerException("Error: No object found.");

		List<ProductIncludeImageDTO> productList= new ArrayList<>();


		for(Product product: products) {

			List<ProductImageDTO> pList= new ArrayList<ProductImageDTO>();
			ProductIncludeImageDTO productDTO= new ProductIncludeImageDTO();
			productDTO.setProduct_id(product.getProduct_id());
			productDTO.setProduct_status_id(product.getProduct_status_id());
			productDTO.setProduct_name(product.getProduct_name());
			productDTO.setDescription_list(product.getDescription_list());
			productDTO.setDescription_details(product.getDescription_details());
			productDTO.setSearch_word(product.getSearch_word());
			productDTO.setPrice(product.getPrice());
			for(Product_Image p: product.getProduct_Image()) {
				ProductImageDTO pDto= new ProductImageDTO();
				if(p.isPrimary() == true) {
					pDto.setName(p.getName());
					pDto.setProduct_image_id(p.getProduct_image_id());
					pDto.setUrl(p.getUrl());
					pList.add(pDto);
				}
			}
			productDTO.setProductImage(pList);
			productList.add(productDTO);
		}
		return productList;
	}

	@Override
	public List<Product> listProductBySKUId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}










}
