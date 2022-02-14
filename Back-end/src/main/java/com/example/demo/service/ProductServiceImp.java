package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.payload.ProductDTO;
import com.example.demo.repository.ProductRepository;

import javassist.NotFoundException;

@Service
public class ProductServiceImp implements ProductService{

	private final ProductRepository productRepository;
	
	public ProductServiceImp(ProductRepository productRepository) {
		super();
		this.productRepository= productRepository;
	}
	
//	public List<ProductDTO> findById(String id) {
//		ProductDTO productDTO= new ProductDTO();
//		Optional<Product> productList= Optional.ofNullable(productRepository.findByProduct_id(id).orElseThrow(() -> new NullPointerException("Error: No object found.")));
//		for (Product) {
//			
//		}
//		productDTO.setDescription_details(product.getDescription_details());
//		productDTO.setDescription_list(product.getDescription_list());
//		productDTO.setDiscount_id(product.getDiscount_id());
//		productDTO.setProduct_name(product.getProduct_name());
//		productDTO.setProduct_status_id(product.getProduct_status_id());
//		productDTO.setSearch_word(product.getSearch_word());
//		return productDTO;
//	}
//	
//	public ProductDTO findByProduct_name(String id) {
//		ProductDTO productDTO= new ProductDTO();
//		Product product= productRepository.findByProduct_name(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
//		productDTO.setDescription_details(product.getDescription_details());
//		productDTO.setDescription_list(product.getDescription_list());
//		productDTO.setDiscount_id(product.getDiscount_id());
//		productDTO.setProduct_name(product.getProduct_name());
//		productDTO.setProduct_status_id(product.getProduct_status_id());
//		productDTO.setSearch_word(product.getSearch_word());
//		return productDTO;
//	}
	

	@Override
	public List<Product> listAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public Product createProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	@Override
	public Product updateProductById(String id, Product productRequest) {
		Product product= productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		product.setDescription_details(productRequest.getDescription_details());
		product.setDescription_list(productRequest.getDescription_list());
		product.setDiscount_id(productRequest.getDiscount_id());
		product.setProduct_name(productRequest.getProduct_name());
		product.setProduct_status_id(productRequest.getProduct_status_id());
		product.setSearch_word(productRequest.getSearch_word());
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(String id) {
		Product product= productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		productRepository.delete(product);
	}

	@Override
	public Product getProductById(String id) {
		Optional<Product> resultOptional= productRepository.findById(id);
		if(resultOptional.isPresent()) {
			return resultOptional.get();
		}
		else {
			throw new NullPointerException("Error: No object found.");
		}
	}
}
