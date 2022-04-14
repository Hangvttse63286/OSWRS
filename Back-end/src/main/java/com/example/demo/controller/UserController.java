package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
import com.example.demo.common.JwtUtils;
import com.example.demo.payload.ChangePassDto;
import com.example.demo.payload.MessageResponse;
import com.example.demo.payload.UpdateUserDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserDetailsImpl;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
    private UserService userService;

	 @Autowired
	 private AuthService authService;

	 @Autowired
	 private JwtUtils jwtUtils;

	@GetMapping("/profile")
//	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> getInfo() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    	    return new ResponseEntity<>(userService.findByUsername(userDetails.getUsername()), HttpStatus.OK);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.UNAUTHORIZED);
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
//    @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
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
    		userService.updateInfo(userDetails.getId() , updateUserDto);
    		return new ResponseEntity<>("Update Info successfully!", HttpStatus.OK);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/changePassword")
//    @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassDto changePassDto) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		if (userService.checkIfValidOldPassword(userDetails.getId(), changePassDto.getOldPassword())) {
    			if (!changePassDto.getNewPassword().equals(changePassDto.getRepeatNewPassword()))
    				return new ResponseEntity<>("Error: Repeat new password doesn't match new password.", HttpStatus.CONFLICT);
    			userService.changePassword(userDetails.getId(), changePassDto.getNewPassword());
    			return new ResponseEntity<>("Change password successfully!", HttpStatus.OK);
    		} else
    			return new ResponseEntity<>("Error: Invalid password.", HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete")
//    @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		userService.deleteUser(userDetails.getId());
    		authService.setLogoutStatus(authentication);
    		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(new MessageResponse("Delete user successfully!"));
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/roles")
//	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> getRoles() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		List<String> roles = userDetails.getAuthorities().stream()
            		.map(item -> item.getAuthority())
            		.collect(Collectors.toList());

    	    return new ResponseEntity<>(roles, HttpStatus.OK);
    	}
    	return new ResponseEntity<>("Error: Logged in first!", HttpStatus.UNAUTHORIZED);
    }

}
