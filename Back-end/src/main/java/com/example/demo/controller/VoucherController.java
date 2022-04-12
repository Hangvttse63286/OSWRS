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

import com.example.demo.payload.ValidateVoucherRequest;
import com.example.demo.payload.VoucherDto;
import com.example.demo.service.VoucherService;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/voucher")
public class VoucherController {
	@Autowired
	VoucherService voucherService;

	@GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllVoucher() {
		List<VoucherDto> voucherList = voucherService.getVoucherList();
        if (!voucherList.isEmpty())
        	return new ResponseEntity<>(voucherList, HttpStatus.OK);
        else
            return new ResponseEntity<>("Error: No voucher found.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getVoucher(@PathVariable Long id) {
        VoucherDto voucher = voucherService.getVoucherById(id);
    	if (voucher != null)
        	return new ResponseEntity<>(voucher, HttpStatus.OK);
        else
            return new ResponseEntity<>("Error: No voucher found.", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createVoucher(@RequestBody VoucherDto voucherDto) {
    	VoucherDto voucher = voucherService.createVoucher(voucherDto);
    	if (voucher == null)
    		return new ResponseEntity<>("Error: Code has already existed.", HttpStatus.CONFLICT);
    	return new ResponseEntity<>(voucher, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateVoucher(@PathVariable Long id, @RequestBody VoucherDto voucherDto) {
    	if (!voucherService.getVoucherById(id).getCode().equals(voucherDto.getCode()))
    		if (voucherService.existsByVoucher(voucherDto.getCode()))
    			return new ResponseEntity<>("Error: Code has already existed.", HttpStatus.CONFLICT);
    	VoucherDto updateResult = voucherService.updateVoucher(id, voucherDto);
    	if (updateResult != null)
    		return new ResponseEntity<>(updateResult, HttpStatus.OK);
    	else
            return new ResponseEntity<>("Error: No voucher found.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/activation/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeActivation(@PathVariable Long id) {
    	VoucherDto updateResult = voucherService.changeVoucherActivation(id);
    	if (updateResult != null)
    		return new ResponseEntity<>(updateResult, HttpStatus.OK);
    	else
            return new ResponseEntity<>("Error: No voucher found.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteVoucher(@PathVariable Long id) {
        if (voucherService.getVoucherById(id) != null) {
        	voucherService.deleteVoucher(id);
        	return new ResponseEntity<>("Delete voucher successfully!", HttpStatus.OK);
        } else
            return new ResponseEntity<>("Error: No voucher found.", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/validate")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> validateVoucher(@RequestBody ValidateVoucherRequest validateVoucherRequest) {
    	int result = voucherService.validateVoucher(validateVoucherRequest.getVoucherCode(), validateVoucherRequest.getCartTotal());
    	switch (result) {
    	case 0:
    		return new ResponseEntity<>("Error: Voucher is not found.", HttpStatus.NOT_FOUND);
    	case 1:
    		return new ResponseEntity<>("Error: Voucher is not activate.", HttpStatus.BAD_REQUEST);
    	case 2:
    		return new ResponseEntity<>("Error: Cart total does not meet voucher requirement.", HttpStatus.PRECONDITION_FAILED);
    	case 3:
    		return new ResponseEntity<>("Error: Not yet time to apply voucher.", HttpStatus.NOT_ACCEPTABLE);
    	case 4:
    		return new ResponseEntity<>("Error: Voucher is expired.", HttpStatus.EXPECTATION_FAILED);
    	case 5:
    		return new ResponseEntity<>("Error: Voucher out of quantity.", HttpStatus.IM_USED);
    	default:
    		return new ResponseEntity<>(voucherService.calculateDiscount(validateVoucherRequest.getVoucherCode(), validateVoucherRequest.getCartTotal()), HttpStatus.OK);
    	}

    }
}
