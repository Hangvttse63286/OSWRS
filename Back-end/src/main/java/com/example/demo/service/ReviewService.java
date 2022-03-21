package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.entity.Products;
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
		Review review = reviewRepository.findById(id).get();
		if (review == null)
			return null;
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setId(review.getId());
		reviewDto.setFullname(review.getUser().getLast_name() + " " + review.getUser().getFirst_name());
		reviewDto.setNumberRating(review.getNumberRating());
		reviewDto.setDescription(review.getDescription());
		return reviewDto;
	}

	public List<ReviewDto> getReviewListByProductId(String id) {
		Products product = productRepository.getById(id);
		List<Review> reviewList = reviewRepository.findByProducts(product);
		if (reviewList.isEmpty())
			return null;
		List<ReviewDto> reviewDtoList = new ArrayList<>();
		for (Review review : reviewList) {
			ReviewDto reviewDto = new ReviewDto();
			reviewDto.setId(review.getId());
			reviewDto.setFullname(review.getUser().getLast_name() + " " + review.getUser().getFirst_name());
			reviewDto.setNumberRating(review.getNumberRating());
			reviewDto.setDescription(review.getDescription());
			reviewDtoList.add(reviewDto);
		}

		return reviewDtoList;
	}

	public Double getAvgRatingByProduct(String id) {
		Products product = productRepository.getById(id);
		List<Review> reviewList = reviewRepository.findByProducts(product);
		if (reviewList.isEmpty())
			return (double) 0;
		Double avg = reviewList.stream().mapToDouble(o -> o.getNumberRating()).average().getAsDouble();
		return avg;
	}

	public ReviewDto createReview (ReviewRequest reviewRequest) {
		Order order = orderRepository.findById(reviewRequest.getOrderId()).get();
		User user = order.getUser();
		Products product = productSKURepository.findById(reviewRequest.getProductSKUId()).get().getProducts();

		Review review = new Review();
		review.setOrder(order);
		review.setUser(user);
		review.setProducts(product);
		review.setNumberRating(reviewRequest.getNumberRating());
		review.setDescription(reviewRequest.getDescription());
		reviewRepository.saveAndFlush(review);
		return getReviewById(review.getId());
	}
}
