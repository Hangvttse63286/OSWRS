package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.ReviewDto;
import com.example.demo.payload.ReviewRequest;
import com.example.demo.service.ReviewService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/review")
public class ReviewController {
	@Autowired
	ReviewService reviewService;

	@PostMapping("/create")
	public ResponseEntity<?> createReview(@RequestBody ReviewRequest reviewRequest) {
		ReviewDto reviewDto = reviewService.createReview(reviewRequest);
		return new ResponseEntity<>(reviewDto, HttpStatus.OK);
	}

	@GetMapping("/{product_id}")
	public ResponseEntity<?> getReviews(@PathVariable String product_id) {
		List<ReviewDto> reviewDtoList = reviewService.getReviewListByProductId(product_id);
		if (!reviewDtoList.isEmpty())
			return new ResponseEntity<>(reviewDtoList, HttpStatus.OK);
		return new ResponseEntity<>("No review available!", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/avg_rating/{product_id}")
	public ResponseEntity<?> getAvgRating(@PathVariable String product_id) {
		Double avgRating = reviewService.getAvgRatingByProduct(product_id);
		if (avgRating != 0)
			return new ResponseEntity<>(avgRating, HttpStatus.OK);
		return new ResponseEntity<>("No review available!", HttpStatus.NOT_FOUND);
	}
}
