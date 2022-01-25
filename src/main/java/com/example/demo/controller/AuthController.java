package com.example.demo.controller;

import com.example.demo.common.ERole;
import com.example.demo.common.JwtUtils;
import com.example.demo.entity.Role;
import com.example.demo.entity.Token;
import com.example.demo.entity.User;
import com.example.demo.payload.JwtResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.MessageResponse;
import com.example.demo.payload.SignUpDto;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie  jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        
        Token newToken = new Token();
        newToken.setToken(jwtCookie.getValue().toString());
        newToken.setUser(userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail()).get());
        newToken.setExpired_at(jwtUtils.getExpiredDateFromToken(jwtCookie.getValue().toString()));
        
        tokenRepository.save(newToken);
        
        
        List<String> roles = userDetails.getAuthorities().stream()
        		.map(item -> item.getAuthority())
        		.collect(Collectors.toList());
        
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        		.body(new JwtResponse(jwtCookie.getValue().toString(), 
        										 userDetails.getId(), 
        										 userDetails.getUsername(), 
        										 userDetails.getEmail(), 
        										 roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Error: Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Error: Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        authService.registerUser(signUpDto);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }
    
	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("You've been signed out!"));
	}
}
