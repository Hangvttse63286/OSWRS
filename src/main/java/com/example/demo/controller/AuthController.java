package com.example.demo.controller;

import com.example.demo.common.ERole;
import com.example.demo.common.JwtUtils;
import com.example.demo.entity.Role;
import com.example.demo.entity.Token;
import com.example.demo.entity.User;
import com.example.demo.payload.JwtResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.SignUpDto;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        Token newToken = new Token();
        newToken.setToken(jwt);
        newToken.setUser(userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail()).get());
        newToken.setExpired_at(jwtUtils.getExpiredDateFromToken(jwt));
        
        tokenRepository.save(newToken);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
        		.map(item -> item.getAuthority())
        		.collect(Collectors.toList());
        
        return ResponseEntity.ok(new JwtResponse(jwt, 
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
        User user = new User();
        user.setFirst_name(signUpDto.getFirst_name());
        user.setLast_name(signUpDto.getLast_name());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setPhone_number(signUpDto.getPhone_number());
        user.setBirthday(signUpDto.getBirthday());
        user.setGender_id(signUpDto.getGender_id());
        user.setDate_joined(Calendar.getInstance().getTime());

        Set<String> strRoles = signUpDto.getRoles();
        Set<Role> roles = new HashSet<>();
        
        if(strRoles == null) {
        	Role userRole = roleRepository.findByName(ERole.ROLE_USER)
        			.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        	roles.add(userRole);
        } else {
        	strRoles.forEach(role -> {
        		switch (role) {
        		case "admin":
        			Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
        				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        			roles.add(adminRole);
        			break;
        		case "staff":
        			Role staffRole = roleRepository.findByName(ERole.ROLE_STAFF)
        				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        			roles.add(staffRole);
        			break;
        		default:
        			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
        				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        			roles.add(userRole);
        		}
        	});
        }
        
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}
