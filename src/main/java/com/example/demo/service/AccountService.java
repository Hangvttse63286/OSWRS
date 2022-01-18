package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.payload.RoleChangeDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.RoleRepository;

@Service
public class AccountService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
	public List<User> findAll () {
		return userRepository.findAll();
	}
	
	public User findById (Long id) {
		return userRepository.findById(id).get();
	}

	public String changeRole (Long id, RoleChangeDto roleChangeDto) {
		User user = userRepository.findById(id).get();
		
		Set<String> strRoles = roleChangeDto.getRoles();
        Set<Role> roles = new HashSet<>();
        
		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).get();
				roles.add(adminRole);
				break;
			case "staff":
				Role staffRole = roleRepository.findByName(ERole.ROLE_STAFF).get();
				roles.add(staffRole);
				break;
			case "user":
				Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
				roles.add(userRole);
				break;
			}
		});
		
		user.setRoles(roles);
		userRepository.save(user);
		return "Change roles successfully!";
	}
	
	public String deleteAcc (Long id) {
		userRepository.deleteById(id);
		return "Delete successfully!";
	}
}
