package com.example.demo.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.JwtUtils;
import com.example.demo.payload.JwtResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.MessageResponse;
import com.example.demo.payload.ResetPasswordDto;
import com.example.demo.payload.SignUpDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginDto loginDto){
        JwtResponse jwtResponse = authService.loginUser(loginDto);
        if(jwtResponse == null) {
        	return new ResponseEntity<>("Error: Unverified account! Please check your email for verification", HttpStatus.BAD_REQUEST);
        }
        	
    	return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtUtils.getJwtCookie(jwtResponse.getAccessToken()).toString())
        		.body(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto, HttpServletRequest req) throws UnsupportedEncodingException, MessagingException{

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Error: Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Error: Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        authService.registerUser(signUpDto,req);

        return ResponseEntity.ok(new MessageResponse("User registered successfully! Check email for verification."));

    }
    
    @GetMapping("signup/verify")
    public ResponseEntity<?> verifyUser(@Param("code") String code) {
    	if(authService.verify(code))
    		return ResponseEntity.ok(new MessageResponse("User verified successfully!"));
    	return new ResponseEntity<>("Error: Email verification failed!", HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/forgot_password")
    public ResponseEntity<?> forgotPassword(@RequestParam(name = "email") String email, HttpServletRequest req) throws UnsupportedEncodingException, MessagingException {
    	if(authService.getUserByEmail(email) == null)
    		return new ResponseEntity<>("Error: Invalid email!", HttpStatus.BAD_REQUEST);
    	authService.updateResetPasswordToken(email, req);
    	return ResponseEntity.ok(new MessageResponse("We hanve sent a reset password link to your email. Please check."));
    }
    
    @GetMapping("/forgot_password/reset")
    public ResponseEntity<?> verifyPasswordResetToken(@Param("token") String token) {
    	if(authService.validatePasswordResetToken(token))
    		return new ResponseEntity<>(token, HttpStatus.OK);
    	return new ResponseEntity<>("Error: Email verification failed!", HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/forgot_password/reset")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
    	if(authService.validatePasswordResetToken(resetPasswordDto.getToken())) {
    		authService.resetPassword(resetPasswordDto);
    		return new ResponseEntity<>("Your password is changed successfully. Please login with the new password.", HttpStatus.OK);
    	}
    	return new ResponseEntity<>("Invalid token!", HttpStatus.BAD_REQUEST);
    }
    
	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authService.setLogoutStatus(authentication);
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("You've been signed out!"));
	}
}
