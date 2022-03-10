package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product_SKU;
import com.example.demo.payload.ProductSkuDTO;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSKURepository;

@Service
public class ProductSKUServiceImpl implements ProductSKUService{

	private final ProductSKURepository productSKURepository;
	
	public ProductSKUServiceImpl(ProductSKURepository productSKURepository) {
		super();
		this.productSKURepository= productSKURepository;
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
			productSkuDTO.setPrice(pSku.getPrice());
			productSkuDTO.setIs_deleted(pSku.isIs_deleted());
			productSkuDTOList.add(productSkuDTO);
		}
		return productSkuDTOList;
	}

	@Override
	public ProductSkuDTO getProductSkuById(Long id) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		
		ProductSkuDTO productSkuDTO= new ProductSkuDTO();
		productSkuDTO.setId(product_SKU.getId());
		productSkuDTO.setStock(product_SKU.getStock());
		productSkuDTO.setSale_limit(product_SKU.getSale_limit());
		productSkuDTO.setSize(product_SKU.getSize());
		productSkuDTO.setPrice(product_SKU.getPrice());
		productSkuDTO.setIs_deleted(product_SKU.isIs_deleted());
		return productSkuDTO;
	}

	@Override
	public void deleteProductSkuById(Long id) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		productSKURepository.delete(product_SKU);
	}

	@Override
	public ProductSkuDTO createProductSku(ProductSkuDTO productSkuDTO) {
		return null;
	}

	@Override
	public ProductSkuDTO updateProductSkuById(Long id, ProductSkuDTO productSkuDTO) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No object found."));
		
		product_SKU.setIs_deleted(productSkuDTO.isIs_deleted());
		product_SKU.setPrice(productSkuDTO.getPrice());
		product_SKU.setSize(productSkuDTO.getSize());
		product_SKU.setSale_limit(productSkuDTO.getSale_limit());
		product_SKU.setStock(productSkuDTO.getStock());
		productSKURepository.save(product_SKU);
		return getProductSkuById(id);
	}

}
