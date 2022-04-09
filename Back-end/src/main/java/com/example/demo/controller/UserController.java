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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;
import com.example.demo.payload.ChangePassDto;
import com.example.demo.payload.UpdateUserDto;
import com.example.demo.service.UserDetailsImpl;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
    private UserService userService;

	@GetMapping("/profile")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> getInfo() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    	    return new ResponseEntity<>(userService.findByUsername(userDetails.getUsername()), HttpStatus.OK);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.PRECONDITION_REQUIRED);
    }

//	@GetMapping("/profile/updateInfo")
//	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
//    public ResponseEntity<?> getUpdateInfo() {
//    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
//    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//    	    return new ResponseEntity<>(userService.findByUsername(userDetails.getUsername()), HttpStatus.OK);
//    	}
//    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.PRECONDITION_REQUIRED);
//    }

    @PostMapping("/profile/updateInfo")
    @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> updateInfo(@RequestBody UpdateUserDto updateUserDto) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		if (!updateUserDto.getUsername().equals(userDetails.getUsername()) || !updateUserDto.getEmail().equals(userDetails.getEmail())) {
    			if (userService.existsByUsername(updateUserDto.getUsername()))
    	    		return new ResponseEntity<>("Error: Username is already taken!", HttpStatus.BAD_REQUEST);
    	    	if (userService.existsByEmail(updateUserDto.getEmail()))
    	    		return new ResponseEntity<>("Error: Email is already taken!", HttpStatus.BAD_REQUEST);
    		}
    		userService.updateInfo(updateUserDto);
    		return new ResponseEntity<>("Update Info successfully!", HttpStatus.OK);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.PRECONDITION_REQUIRED);
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassDto changePassDto) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		if (userService.checkIfValidOldPassword(userDetails.getId(), changePassDto.getOldPassword())) {
    			userService.changePassword(userDetails.getId(), changePassDto.getNewPassword());
    			return new ResponseEntity<>("Change password successfully!", HttpStatus.OK);
    		} else
    			return new ResponseEntity<>("Error: Invalid password.", HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.PRECONDITION_REQUIRED);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteUSer() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		userService.deleteUser(userDetails.getId());
    		return new ResponseEntity<>("Delete user successfully!", HttpStatus.OK);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.PRECONDITION_REQUIRED);
    }
}
