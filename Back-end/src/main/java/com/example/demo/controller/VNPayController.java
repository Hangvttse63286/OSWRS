package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.VNPayPaymentRequest;
import com.example.demo.payload.VNPayPaymentResponse;
import com.example.demo.payload.VnPayQueryRequest;
import com.example.demo.payload.VnPayQueryResponse;
import com.example.demo.service.VNPayService;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payment/vnpay")
public class VNPayController {

	@Autowired
	private VNPayService vnpService;

	@PostMapping("/")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createVnpPayment(@RequestBody VNPayPaymentRequest vnpRequest, HttpServletRequest req) throws IOException {
		try {
			VNPayPaymentResponse vnpResponse = vnpService.vnpCreatePayment(vnpRequest, req);
    		return new ResponseEntity<>(vnpResponse, HttpStatus.OK);
		} catch (Exception e) {
    	return new ResponseEntity<>("Error: Can't create payment", HttpStatus.BAD_REQUEST);
		}
    }

	@GetMapping("/return")
//	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getVnpPaymentResult(HttpServletRequest req) throws IOException {
		int vnpResponse = vnpService.checkResult(req);
    	switch (vnpResponse) {
    	 case 1:
    		 return new ResponseEntity<>("Payment successfully!", HttpStatus.OK);
    	 case 2:
    		 return new ResponseEntity<>("Error: Payment failed!", HttpStatus.EXPECTATION_FAILED);
    	 default:
    		 return new ResponseEntity<>("Error: Signature is invalid!", HttpStatus.BAD_REQUEST);
    	}
	}

//	@GetMapping("/query")
//	@PreAuthorize("hasRole('ADMIN')")
//	public ResponseEntity<?> query(@RequestBody VnPayQueryRequest vnpRequest, HttpServletRequest req) throws IOException {
//		VnPayQueryResponse vnpResponse = vnpService.vnpQuery(vnpRequest, req);
//    	if(vnpResponse != null)
//    		return new ResponseEntity<>(vnpResponse, HttpStatus.OK);
//    	return new ResponseEntity<>("Error: Can't query payment", HttpStatus.NOT_FOUND);
//	}
}
