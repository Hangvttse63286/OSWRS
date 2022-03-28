package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.ProductRecommendationResponse;
import com.example.demo.payload.RecommendationSaveRequest;
import com.example.demo.service.RecommendationService;
import com.example.demo.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/recommendation")
public class RecommedationController {
	@Autowired
	private RecommendationService recommendationService;

	@GetMapping("/send_all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<?> sendImageUrl() {
		List<String> imageUrlList = recommendationService.getAllImage();
		if (!imageUrlList.isEmpty())
			return new ResponseEntity<>(imageUrlList, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/")
	public ResponseEntity<?> getByUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		List<ProductRecommendationResponse> productList = recommendationService.getRecommendedProductsByUser(userDetails.getUsername());
    		return new ResponseEntity<>(productList, HttpStatus.OK);
    	}
		List<ProductRecommendationResponse> productList = recommendationService.getRecommendedProductsByUser(null);
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<?> getRecommendedProducts(@RequestBody RecommendationSaveRequest recommendationSaveRequest) {
		int result = recommendationService.saveRecommendedProducts(recommendationSaveRequest.getImageUrlList(), recommendationSaveRequest.getUsername());
		if (result == 0)
			return new ResponseEntity<>("Error: User not found!", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>("Save successfully!", HttpStatus.OK);
	}
}
