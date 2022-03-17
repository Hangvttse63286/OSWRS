package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Product_SKU;
import com.example.demo.payload.ProductImageDTO;
import com.example.demo.payload.ProductSkuDTO;

public interface ProductSKUService {
	List<ProductSkuDTO> listAllProductSku();
	ProductSkuDTO getSkuById(Long id);
	
	void deleteProductSkuById(Long id);
	Product_SKU createProductSku(String id, ProductSkuDTO productSkuDTO);
	ProductSkuDTO updateProductSkuById(Long id, ProductSkuDTO productSkuDTO);
	List<ProductSkuDTO> getSKUByProductId(String id);
	
}
