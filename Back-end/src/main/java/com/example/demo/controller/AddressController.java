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

import com.example.demo.payload.AddressDto;
import com.example.demo.service.AddressService;
import com.example.demo.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/address")
public class AddressController {
	@Autowired
    private AddressService addressService;

	@GetMapping("/")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAddressList() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		List<AddressDto> addressList = addressService.getAddressList(userDetails.getUsername());
    		if (!addressList.isEmpty())
    			return new ResponseEntity<>(addressList, HttpStatus.OK);
    		return new ResponseEntity<>("No address found", HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.PRECONDITION_REQUIRED);
    }

	@PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createAddress(@RequestBody AddressDto createAddressDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		return new ResponseEntity<>(addressService.createAddress(createAddressDto, userDetails.getUsername()), HttpStatus.OK);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.PRECONDITION_REQUIRED);
    }

	@PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody AddressDto updateAddressDto) {
    	AddressDto address = addressService.updateAddress(id, updateAddressDto);
    	if (address != null)
    		return new ResponseEntity<>(addressService.updateAddress(id, updateAddressDto), HttpStatus.OK);

    	return new ResponseEntity<>("No address found", HttpStatus.NOT_FOUND);
    }

	@DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
		if (addressService.getAddressById(id) != null) {
			addressService.deleteAddressById(id);
			return new ResponseEntity<>("Delete address successfully!", HttpStatus.OK);
		} else
            return new ResponseEntity<>("No address found", HttpStatus.NOT_FOUND);
    }
}
