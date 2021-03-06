package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.payload.ReviewDto;
import com.example.demo.payload.ReviewRequest;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSKURepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

@Service
public class ReviewService {
	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductSKURepository productSKURepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ReviewRepository reviewRepository;

	public ReviewDto getReviewById(Long id) {
		try {
			Review review = reviewRepository.findById(id).get();

			ReviewDto reviewDto = new ReviewDto();
			reviewDto.setId(review.getId());
			if (review.getUser() !=null)
				reviewDto.setFullname(review.getUser().getLast_name() + " " + review.getUser().getFirst_name());
			else
				reviewDto.setFullname("Khách hàng");
			reviewDto.setNumberRating(review.getNumberRating());
			reviewDto.setDescription(review.getDescription());
			return reviewDto;
		} catch (Exception e) {
			return null;
		}

	}

	public ReviewDto getReviewDto(Review review) {
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setId(review.getId());
		if (review.getUser() !=null)
			reviewDto.setFullname(review.getUser().getLast_name() + " " + review.getUser().getFirst_name());
		else
			reviewDto.setFullname("Khách hàng");
		reviewDto.setDescription(review.getDescription());
		return reviewDto;
	}

	public List<ReviewDto> getReviewListByProductId(String id) {
		Product product = productRepository.getById(id);
		List<Review> reviewList = reviewRepository.findByProducts(product);
		if (reviewList.isEmpty())
			return new ArrayList<>();
		List<ReviewDto> reviewDtoList = new ArrayList<>();
		for (Review review : reviewList) {
			ReviewDto reviewDto = new ReviewDto();
			reviewDto.setId(review.getId());
			if (review.getUser() !=null)
				reviewDto.setFullname(review.getUser().getLast_name() + " " + review.getUser().getFirst_name());
			else
				reviewDto.setFullname("Khách hàng");
			reviewDto.setNumberRating(review.getNumberRating());
			reviewDto.setDescription(review.getDescription());
			reviewDtoList.add(reviewDto);
		}

		return reviewDtoList;
	}

	public Double getAvgRatingByProduct(String id) {
		Product product = productRepository.getById(id);
		List<Review> reviewList = reviewRepository.findByProducts(product);
		if (reviewList.isEmpty())
			return (double) 0;
		Double avg = reviewList.stream().mapToDouble(o -> o.getNumberRating()).average().getAsDouble();
		return avg;
	}

	public ReviewDto createReview (ReviewRequest reviewRequest) {
		Order order = orderRepository.findById(reviewRequest.getOrderId())
				.orElseThrow(() -> new NullPointerException("Error: No order found."));

		User user = order.getUser();

		Product_SKU productSKU = productSKURepository.findById(reviewRequest.getProductSKUId())
				.orElseThrow(() -> new NullPointerException("Error: No product sku found."));

		Product product = productSKU.getProducts();

		Review review = new Review();
		review.setOrder(order);
		review.setUser(user);
		review.setProducts(product);
		review.setNumberRating(reviewRequest.getNumberRating());
		review.setDescription(reviewRequest.getDescription());
		reviewRepository.saveAndFlush(review);
		return getReviewDto(review);
	}
}
