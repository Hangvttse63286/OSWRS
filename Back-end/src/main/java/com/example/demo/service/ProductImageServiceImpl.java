package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Product_Image;
import com.example.demo.payload.ProductImageDTO;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductSKURepository;

public class ProductImageServiceImpl implements ProductImageService{

	private final ProductSKURepository productSKURepository;
	private final ProductImageRepository productImageReository;
	
	
	public ProductImageServiceImpl(ProductSKURepository productSKURepository,
			ProductImageRepository productImageReository) {
		super();
		this.productSKURepository = productSKURepository;
		this.productImageReository = productImageReository;
	}


	@Override
	public List<ProductImageDTO> listProductImage() {
		List<ProductImageDTO> productImageDTOList= new ArrayList<ProductImageDTO>();
		
		for(Product_Image p: productImageReository.findAll()) {
			ProductImageDTO productImageDTO= new ProductImageDTO();
			
			productImageDTO.setName(p.getName());
			productImageDTO.setProduct_image_id(p.getProduct_image_id());
			productImageDTO.setUrl(p.getUrl());
			productImageDTOList.add(productImageDTO);
		}
		return productImageDTOList;			
	}


	@Override
	public List<ProductImageDTO> listProductImageByProductId() {
		// TODO Auto-generated method stub
		return null;
	}

}
