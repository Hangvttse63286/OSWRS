package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.OrderDto;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserDetailsImpl;
import com.example.demo.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/order")
public class OrderControllerUser {
	@Autowired
	OrderService orderService;
	
	@GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllOrder() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		if (!orderService.getOrderListByUser(userDetails.getUsername()).isEmpty())
            	return new ResponseEntity<>(orderService.getOrderListByUser(userDetails.getUsername()), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.PRECONDITION_REQUIRED);
		
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        if (orderService.getOrderById(id) != null)
        	return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
    	return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        if (orderService.getOrderById(id) != null) {
        	orderService.deleteOrder(id);
        	return new ResponseEntity<>("Delete order successfully!", HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}