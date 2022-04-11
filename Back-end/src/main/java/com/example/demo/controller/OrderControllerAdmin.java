package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.OrderDto;
import com.example.demo.payload.OrderStatusDto;
import com.example.demo.payload.VoucherDto;
import com.example.demo.service.OrderService;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/order")
public class OrderControllerAdmin {
	@Autowired
	OrderService orderService;

	@GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getAllOrder() {
		List<OrderDto> orderList = orderService.getOrderList();
		if (!orderList.isEmpty())
        	return new ResponseEntity<>(orderList, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
    	if (order != null)
        	return new ResponseEntity<>(order, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
    	return new ResponseEntity<>(orderService.createOrder(orderDto, orderDto.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/change_status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long id, OrderStatusDto orderStatusDto) {
    	OrderDto updateResult = orderService.changeOrderStatus(id, orderStatusDto);
    	if (updateResult != null)
    		return new ResponseEntity<>(updateResult, HttpStatus.OK);
    	else
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 }
    
    @GetMapping("/delivery/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getDeliveryOrder(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
    	if (order != null)
        	return new ResponseEntity<>(order, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PutMapping("/update/{id}")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
//    public ResponseEntity<?> updateVoucher(@PathVariable Long id, @RequestBody VoucherDto voucherDto) {
//    	if (voucherService.getVoucherByCode(voucherDto.getCode()) != null)
//    		return new ResponseEntity<>("Error: Code has already existed.", HttpStatus.BAD_REQUEST);
//    	VoucherDto updateResult = voucherService.updateVoucher(id, voucherDto);
//    	if (updateResult != null)
//    		return new ResponseEntity<>(updateResult, HttpStatus.OK);
//    	else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PutMapping("/change_status/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> changeOrderStatus(@PathVariable Long id) {
//    	VoucherDto updateResult = voucherService.changeVoucherActivation(id);
//    	if (updateResult != null)
//    		return new ResponseEntity<>(updateResult, HttpStatus.OK);
//    	else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
//    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
//        if (orderService.getOrderById(id) != null) {
//        	orderService.deleteOrder(id);
//        	return new ResponseEntity<>("Delete order successfully!", HttpStatus.OK);
//        } else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
