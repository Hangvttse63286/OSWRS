package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product_Image;
import com.example.demo.payload.ProductImageDTO;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductSKURepository;
@Service
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
	public List<ProductImageDTO> listImage() {
		List<ProductImageDTO> productImageDTOList= new ArrayList<ProductImageDTO>();
		
		for(Product_Image p: productImageReository.findAll()) {
			ProductImageDTO productImageDTO= new ProductImageDTO();
			if(p.isPrimary() == true) {
				productImageDTO.setName(p.getName());
				productImageDTO.setProduct_image_id(p.getProduct_image_id());
				productImageDTO.setUrl(p.getUrl());
				productImageDTO.setPrimaries(p.isPrimary() );
				productImageDTOList.add(productImageDTO);
			}
		}	
		return productImageDTOList;			
	}

}
