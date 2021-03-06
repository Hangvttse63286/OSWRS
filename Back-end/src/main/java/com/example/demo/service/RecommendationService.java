package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product_Image;
import com.example.demo.entity.Recommendation;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.payload.ProductRecommendationResponse;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

@Service
public class RecommendationService {
	@Autowired
	ProductImageRepository productImageRepository;

	@Autowired
	RecommendationRepository recommendationRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserRepository userRepository;

	public List<String> getAllImage() {
		List<Product_Image> imageList = productImageRepository.findByPrimaries(true);
		if (imageList.isEmpty())
			return new ArrayList<String>();
		List<String> imageUrlList = imageList.stream().map(Product_Image::getUrl).collect(Collectors.toList());
		return imageUrlList;
	}

	public List<ProductRecommendationResponse> getRecommendedProductsByUser (String username, boolean flag) {
		List<Product> productList;
		if (flag) {
			User user = userRepository.findByUsername(username).get();
			List<Recommendation> recommendationList = recommendationRepository.findByUser(user);
			if(!recommendationList.isEmpty()) {
				productList = recommendationList.stream().map(Recommendation::getProduct).collect(Collectors.toList());
			}
			else {
				productList = productRepository.findAllByOrderBySoldDesc();
			}
		}
		else {
			productList = productRepository.findAllByOrderBySoldDesc();
		}
		List<ProductRecommendationResponse> productResponseList = new ArrayList<>();
		for (Product product : productList) {
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

	public int saveRecommendedProducts (List<String> imageUrlList, String username) {
		try {
			User user = userRepository.findByUsername(username).get();

			for (String imageUrl : imageUrlList) {
				Product_Image image = productImageRepository.findByUrl(imageUrl).get();
				Product product = image.getProducts();
				Recommendation recommedation = new Recommendation();
				recommedation.setUser(user);
				recommedation.setProduct(product);
				recommendationRepository.saveAndFlush(recommedation);
			}
			return 1;
		} catch (Exception e) {
			return 0;
		}

	}

	public List<ProductRecommendationResponse> getRecommendedProductList(List<String> imageUrlList) {

		List<Product> productList = new ArrayList<>();

		for (String imageUrl : imageUrlList) {
			Product_Image image = productImageRepository.findByUrl(imageUrl).get();
			Product product = image.getProducts();
			productList.add(product);
		}

		List<ProductRecommendationResponse> productResponseList = new ArrayList<>();
		for (Product product : productList) {
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

	public List<String> getLatestBoughtImagesByUser(String username) {

		if (username.isEmpty())
			return new ArrayList<String>();

		User user = userRepository.findByUsername(username).get();
		if (orderRepository.findByUser(user).isEmpty())
			return new ArrayList<String>();

		List<String> imageUrlList = new ArrayList<>();
		List<Order> orderList = orderRepository.findByUserOrderByIdDesc(user);
		Map<Product, String> productMap = new HashMap<>();
		for (Order order : orderList) {
			Set<OrderItem> orderItems = order.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				Product product = orderItem.getProductSKU().getProducts();
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

	public List<String> getImageByProduct(String productId) {
		Product product = productRepository.findById(productId).get();
		Set<Product_Image> imageList = product.getProduct_Image();
		List<String> imageUrlList = new ArrayList<>();
		for(Product_Image image : imageList) {
			if (image.isPrimary())
				imageUrlList.add(image.getUrl());
		}
//		List<Product_Image> imageList = productImageRepository.findByProductsAndPrimaries(product, true);
//		List<String> imageUrlList = imageList.stream().map(Product_Image::getUrl).collect(Collectors.toList());
		return imageUrlList;
	}
}
