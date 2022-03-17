package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.Products;
import com.example.demo.payload.ProductSkuDTO;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSKURepository;

@Service
public class ProductSKUServiceImpl implements ProductSKUService{

	private final ProductSKURepository productSKURepository;
	private final ProductRepository productRepository;
	
	public ProductSKUServiceImpl(ProductSKURepository productSKURepository, ProductRepository productRepository) {
		super();
		this.productSKURepository= productSKURepository;
		this.productRepository= productRepository;
	}
	
    private long id;
	private int stock;
	private int sale_limit;
	private String size;
	private float price;
	private boolean is_deleted;
	
	@Override
	public List<ProductSkuDTO> listAllProductSku() {
		List<Product_SKU> list= productSKURepository.findAll();
		if(list.isEmpty()) {
			throw new NullPointerException("Error: No object found.");
		}
		List<ProductSkuDTO> productSkuDTOList= new ArrayList<ProductSkuDTO>();
		for(Product_SKU pSku: list) {
			ProductSkuDTO productSkuDTO= new ProductSkuDTO();
			productSkuDTO.setId(pSku.getId());
			productSkuDTO.setStock(pSku.getStock());
			productSkuDTO.setSale_limit(pSku.getSale_limit());
			productSkuDTO.setSize(pSku.getSize());
			productSkuDTO.setIs_deleted(pSku.isIs_deleted());
			productSkuDTOList.add(productSkuDTO);
		}
		return productSkuDTOList;
	}
	
	@Override
	public void deleteProductSkuById(Long id) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		productSKURepository.delete(product_SKU);
	}


	@Override
	public ProductSkuDTO updateProductSkuById(Long id, ProductSkuDTO productSkuDTO) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		
		product_SKU.setIs_deleted(productSkuDTO.isIs_deleted());
		product_SKU.setSize(productSkuDTO.getSize());
		product_SKU.setSale_limit(productSkuDTO.getSale_limit());
		product_SKU.setStock(productSkuDTO.getStock());
		productSKURepository.save(product_SKU);
		return getSkuById(id);
	}

	@Override
	public ProductSkuDTO getSkuById(Long id) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		
		ProductSkuDTO productSkuDTO= new ProductSkuDTO();
		productSkuDTO.setId(product_SKU.getId());
		productSkuDTO.setStock(product_SKU.getStock());
		productSkuDTO.setSale_limit(product_SKU.getSale_limit());
		productSkuDTO.setSize(product_SKU.getSize());
		productSkuDTO.setIs_deleted(product_SKU.isIs_deleted());
		return productSkuDTO;
	}

	@Override
	public List<ProductSkuDTO> getSKUByProductId(String id) {
		Products products = productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		
		List<ProductSkuDTO> pList= new ArrayList<ProductSkuDTO>();
		for(Product_SKU p: products.getProductSKUs()) {
			ProductSkuDTO productSkuDTO= new ProductSkuDTO();
			productSkuDTO.setId(p.getId());
			productSkuDTO.setIs_deleted(p.isIs_deleted());
			productSkuDTO.setSale_limit(p.getSale_limit());
			productSkuDTO.setSize(p.getSize());
			productSkuDTO.setStock(p.getStock());
			pList.add(productSkuDTO);
		}
		return pList;
	}

	@Override
	public Product_SKU createProductSku(String id, ProductSkuDTO productRequest) {
		Products products = productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		Set<Product_SKU> product_SKU_List= new HashSet<Product_SKU>();

		Product_SKU product_SKU= new Product_SKU();
		product_SKU.setId(productRequest.getId());
		product_SKU.setStock(productRequest.getStock());
		product_SKU.setSale_limit(productRequest.getSale_limit());
		product_SKU.setSize(productRequest.getSize());
		product_SKU.setIs_deleted(productRequest.isIs_deleted());
		product_SKU.setProducts(productRepository.findById(products.getProduct_id()).get());
		product_SKU_List.add(product_SKU);
		products.setProductSKUs(product_SKU_List);
		
		return productSKURepository.save(product_SKU);
	}



}
