package com.example.demo.service;

import java.util.List;

import com.example.demo.payload.ProductImageDTO;

public interface ProductImageService {
	List<ProductImageDTO> listProductImage();
	List<ProductImageDTO> listProductImageByProductId();
}
