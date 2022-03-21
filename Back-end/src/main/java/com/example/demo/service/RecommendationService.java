package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product_Image;
import com.example.demo.entity.Products;
import com.example.demo.entity.User;
import com.example.demo.payload.ProductRecommendationResponse;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

@Service
public class RecommendationService {
	@Autowired
	ProductImageRepository productImageRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserRepository userRepository;

	public List<String> getAllImage() {
		List<Product_Image> imageList = productImageRepository.findAll();
		if (imageList.isEmpty())
			return null;
		List<String> imageUrlList = new ArrayList<>();
		for (Product_Image productImage : imageList) {
			if(productImage.isPrimary())
				imageUrlList.add(productImage.getUrl());
		}
		return imageUrlList;
	}

	public List<ProductRecommendationResponse> getMostSoldProducts () {
		List<Products> productList = productRepository.findAllByOrderBySoldDesc();
		List<ProductRecommendationResponse> productResponseList = new ArrayList<>();
		for (Products product : productList) {
			ProductRecommendationResponse productResponse = new ProductRecommendationResponse();
			productResponse.setProduct_id(product.getProduct_id());
			productResponse.setProduct_name(product.getProduct_name());
			productResponse.setProduct_status_id(product.getProduct_status_id());
			productResponse.setPrice(product.getPrice());
			Set<Product_Image> imageList = product.getProduct_Image();
			for(Product_Image image : imageList) {
				if (image.isPrimary())
					productResponse.setImageUrl(image.getUrl());
			}
			productResponseList.add(productResponse);
			if (productResponseList.size() == 10)
				break;
		}
		return productResponseList;
	}

	public List<ProductRecommendationResponse> getRecommendedProducts (List<String> imageUrlList) {
		List<ProductRecommendationResponse> productResponseList = new ArrayList<>();
		for (String imageUrl : imageUrlList) {
			Product_Image image = productImageRepository.findByUrl(imageUrl).get();
			Products product = image.getProducts();
			ProductRecommendationResponse productResponse = new ProductRecommendationResponse();
			productResponse.setProduct_id(product.getProduct_id());
			productResponse.setProduct_name(product.getProduct_name());
			productResponse.setProduct_status_id(product.getProduct_status_id());
			productResponse.setPrice(product.getPrice());
			productResponse.setImageUrl(image.getUrl());
			productResponseList.add(productResponse);
		}
		return productResponseList;
	}

	public List<String> getLatestBoughtImagesByUser(String username) {
		List<String> imageUrlList = new ArrayList<>();
		if (username.isEmpty() || orderRepository.findByUser(userRepository.findByUsername(username).get()).isEmpty())
			return null;
		User user = userRepository.findByUsername(username).get();
		List<Order> orderList = orderRepository.findByUserOrderByOrderDateDesc(user);
		Map<Products, String> productMap = new HashMap<>();
		for (Order order : orderList) {

			for (OrderItem orderItem : order.getOrderItems()) {
				Products product = orderItem.getProductSKU().getProducts();
				if (product.getProduct_status_id().equalsIgnoreCase("instock") && !productMap.containsKey(product)) {
					Set<Product_Image> imageList = product.getProduct_Image();
					for (Product_Image image : imageList) {
						if (image.isPrimary())
							productMap.put(product, image.getUrl());
					}
				}
				if (productMap.size() == 10)
					break;
			}
			if (productMap.size() == 10)
				break;
		}
		imageUrlList.addAll(productMap.values());
		return imageUrlList;
	}
}
