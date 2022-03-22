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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CartService;
import com.example.demo.service.UserDetailsImpl;
import com.example.demo.service.UserService;

import com.example.demo.payload.CartItemDto;
import com.example.demo.payload.CartItemResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
    private CartService cartService;

	@Autowired
    private UserService userService;

	@GetMapping("/")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCartList() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		List<CartItemResponse> cartItemList = cartService.getCartItemList(userDetails.getUsername());
    		if (!cartItemList.isEmpty())
    			return new ResponseEntity<>(cartItemList, HttpStatus.FOUND);
    		return new ResponseEntity<>("No item found!", HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.PRECONDITION_REQUIRED);
    }

	@PostMapping("/add")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToCart(@RequestBody CartItemDto cartItemDto) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		if (cartService.checkStock(cartItemDto.getProductSKUId(), cartItemDto.getQuantity())) {
    			CartItemResponse cartItemResponse = cartService.addToCart(cartItemDto, userDetails.getUsername());
    			return new ResponseEntity<>(cartItemResponse, HttpStatus.CREATED);

    		}
    		return new ResponseEntity<>("Quantity exceeds product's stock!", HttpStatus.NOT_ACCEPTABLE);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.PRECONDITION_REQUIRED);
    }

	@PutMapping("/change_quantity")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changeQuantity(@RequestBody CartItemDto cartItemDto) {
		if (!cartService.isExistCartItem(cartItemDto.getId()))
			return new ResponseEntity<>("No item found!", HttpStatus.NOT_FOUND);
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		if (cartService.checkStock(cartItemDto.getProductSKUId(), cartItemDto.getQuantity())) {
    			CartItemResponse cartItemResponse = cartService.changeQuantity(cartItemDto);
    			return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    		}
    		return new ResponseEntity<>("Quantity exceeds product's stock!", HttpStatus.NOT_ACCEPTABLE);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.PRECONDITION_REQUIRED);
    }

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
		if (!cartService.isExistCartItem(id))
			return new ResponseEntity<>("No item found!", HttpStatus.NOT_FOUND);
    	cartService.deleteCartItem(id);
    	return new ResponseEntity<>("Delete successfully!", HttpStatus.OK);
    }

}
