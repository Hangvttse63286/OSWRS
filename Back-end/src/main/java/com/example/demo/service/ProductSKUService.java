package com.example.demo.service;

import java.util.List;

import com.example.demo.payload.ProductSkuDTO;

public interface ProductSKUService {
	List<ProductSkuDTO> listAllProductSku();
	ProductSkuDTO getProductSkuById(Long id);
	void deleteProductSkuById(Long id);
	ProductSkuDTO createProductSku(ProductSkuDTO productSkuDTO);
	ProductSkuDTO updateProductSkuById(Long id, ProductSkuDTO productSkuDTO);
}
