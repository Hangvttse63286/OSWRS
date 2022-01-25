package com.example.demo.service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.common.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.Verification;
import com.example.demo.payload.SignUpDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationRepository;

import net.bytebuddy.utility.RandomString;

import com.example.demo.repository.RoleRepository;

@Service
public class AuthService {
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private VerificationRepository verificationRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public boolean existsByEmail(String email) {
		if(userRepository.existsByEmail(email)){
	        return true;
	    }
		return false;
	}
	
	public boolean existsByUsername(String username) {
		if(userRepository.existsByUsername(username)){
	        return true;
	    }
		return false;
	}
	
	public void registerUser (SignUpDto signUpDto) {
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
        
        Verification verification = new Verification();
        verification.setEnabled(false);
        verification.setVerificationCode(RandomString.make(64));
        verification.setUser(user);
        
        user.setVerification(verification);
        verificationRepository.save(verification);
        userRepository.save(user);
        
	}
}
