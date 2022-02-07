package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.common.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.payload.RoleChangeDto;
import com.example.demo.payload.UpdateUserDto;
import com.example.demo.payload.UserDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.RoleRepository;

@Service
public class AccountService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
	public List<UserDto> findAll () {
		List<User> userList = userRepository.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		for (User user : userList) {
			UserDto userDto = new UserDto();
			userDto.setFirst_name(user.getFirst_name());
			userDto.setLast_name(user.getLast_name());
			userDto.setEmail(user.getEmail());
			userDto.setUsername(user.getUsername());
			userDto.setPhone_number(user.getPhone_number());
			userDto.setGender_id(user.getGender_id());
			userDto.setBirthday(user.getBirthday());
			List<String> roles = user.getRoles().stream()
	        		.map(item -> item.getName().toString())
	        		.collect(Collectors.toList());
			userDto.setRoles(roles);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}
	
	public UserDto findById (Long id) {
		UserDto userDto = new UserDto();
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Error: No object found."));
		userDto.setFirst_name(user.getFirst_name());
		userDto.setLast_name(user.getLast_name());
		userDto.setEmail(user.getEmail());
		userDto.setUsername(user.getUsername());
		userDto.setPhone_number(user.getPhone_number());
		userDto.setGender_id(user.getGender_id());
		userDto.setBirthday(user.getBirthday());
		List<String> roles = user.getRoles().stream()
        		.map(item -> item.getName().toString())
        		.collect(Collectors.toList());
		userDto.setRoles(roles);
		
		return userDto;
	}

	public String changeRole (Long id, RoleChangeDto roleChangeDto) {
		User user = userRepository.findById(id).get();
		
		Set<String> strRoles = roleChangeDto.getRoles();
        Set<Role> roles = new HashSet<>();
        
		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Role not found:" + role));
				roles.add(adminRole);
				break;
			case "staff":
				Role staffRole = roleRepository.findByName(ERole.ROLE_STAFF)
						.orElseThrow(() -> new RuntimeException("Role not found:" + role));
				roles.add(staffRole);
				break;
			case "user":
				Role userRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Role not found:" + role));
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
