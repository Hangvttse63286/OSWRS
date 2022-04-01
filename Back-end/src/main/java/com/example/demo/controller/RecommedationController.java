package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import org.springframework.web.client.RestTemplate;

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

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/send_all")
	public ResponseEntity<?> sendImageUrl() {
		List<String> imageUrlList = recommendationService.getAllImage();
		if (!imageUrlList.isEmpty()) {
			String URL = "";
    		HttpHeaders headers = new HttpHeaders();
    	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	    headers.setContentType(MediaType.APPLICATION_JSON);
    	    HttpEntity<Object> entity = new HttpEntity<Object>(imageUrlList, headers);
    		restTemplate.exchange(
    			    URL,
    			    HttpMethod.GET,
    			    entity,
    			    Void.class);
    		return new ResponseEntity<>("Send successfully!", HttpStatus.OK);
		}
		return new ResponseEntity<>("No image found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/list")
	public ResponseEntity<?> getByUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		List<ProductRecommendationResponse> productList = recommendationService.getRecommendedProductsByUser(userDetails.getUsername(), true);
    		return new ResponseEntity<>(productList, HttpStatus.OK);
    	}
		List<ProductRecommendationResponse> productList = recommendationService.getRecommendedProductsByUser(null, false);
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}

	@GetMapping("/save")
	public ResponseEntity<?> saveRecommendedProducts() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		String URL = "https://my-recommendation-api.herokuapp.com/recommend";
    		HttpHeaders headers = new HttpHeaders();
    	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	    headers.setContentType(MediaType.APPLICATION_JSON);
    	    HttpEntity<List<String>> entity = new HttpEntity<List<String>>(recommendationService.getLatestBoughtImagesByUser(userDetails.getUsername()), headers);
    	    ResponseEntity<String> response = restTemplate.postForEntity(URL, entity, String.class);
    	    String stringResult = response.getBody().replace(" ", "");
    		List<String> imageReturnList = Arrays.asList(stringResult.substring(1, stringResult.length() - 2).replace("'", "").split(","));
    		int result = recommendationService.saveRecommendedProducts(imageReturnList, userDetails.getUsername());
    		if (result == 0)
    			return new ResponseEntity<>("Error: User not found!", HttpStatus.NOT_FOUND);
    		return new ResponseEntity<>("Save successfully!", HttpStatus.OK);
    	}
		return new ResponseEntity<>("Error: Please log in first!", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/get")
	public ResponseEntity<?> getLatestBoughtImagesByUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    		List<String> imageList = recommendationService.getLatestBoughtImagesByUser(userDetails.getUsername());
    		String URL = "https://my-recommendation-api.herokuapp.com/recommend";
    		HttpHeaders headers = new HttpHeaders();
    	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	    headers.setContentType(MediaType.APPLICATION_JSON);
    	    HttpEntity<List<String>> entity = new HttpEntity<List<String>>(imageList, headers);
    	    ResponseEntity<String> response = restTemplate.postForEntity(URL, entity, String.class);
    		String stringResult = response.getBody().replace(" ", "");
    		List<String> imageReturnList = Arrays.asList(stringResult.substring(1, stringResult.length() - 2).replace("'", "").split(","));
    		return new ResponseEntity<>(imageReturnList, HttpStatus.OK);
    	}
		return new ResponseEntity<>("Error: Please log in first!", HttpStatus.UNAUTHORIZED);
	}

}
