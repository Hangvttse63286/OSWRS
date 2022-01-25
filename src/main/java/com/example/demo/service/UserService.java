package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.payload.UpdateUserDto;
import com.example.demo.payload.UserDto;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
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
		
		return userDto;
	}
	
	public UserDto findByUsername(String username) {
		UserDto userDto = new UserDto();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NullPointerException("Error: No object found."));
		userDto.setFirst_name(user.getFirst_name());
		userDto.setLast_name(user.getLast_name());
		userDto.setEmail(user.getEmail());
		userDto.setUsername(user.getUsername());
		userDto.setPhone_number(user.getPhone_number());
		userDto.setGender_id(user.getGender_id());
		userDto.setBirthday(user.getBirthday());
		
		return userDto;
	}
	
	
	public void updateInfo (UpdateUserDto updateUserDto) {
		User user = userRepository.findById(updateUserDto.getId()).get();
		
        user.setFirst_name(updateUserDto.getFirst_name());
        user.setLast_name(updateUserDto.getLast_name());
        user.setUsername(updateUserDto.getUsername());
        user.setEmail(updateUserDto.getEmail());
        user.setPhone_number(updateUserDto.getPhone_number());
        user.setBirthday(updateUserDto.getBirthday());
        user.setGender_id(updateUserDto.getGender_id());
        
        userRepository.save(user);
	}
	
	public void changePassword (Long id, String newPassword) {
		User user = userRepository.getById(id);
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}
	
	public boolean checkIfValidOldPassword (Long id, String oldPassword) {
		if (userRepository.getById(id).getPassword().equals(passwordEncoder.encode(oldPassword)))
			return true;
		return false;
	}
	
	public void deleteUser (Long id) {
		userRepository.deleteById(id);
	}
	
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
}
