package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Address;
import com.example.demo.entity.Order;
import com.example.demo.entity.Recommendation;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.payload.UpdateUserDto;
import com.example.demo.payload.UserDto;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
    private OrderRepository orderRepository;

	@Autowired
    private ReviewRepository reviewRepository;

	@Autowired
    private RecommendationRepository recommendationRepository;

	@Autowired
    private AddressRepository addressRepository;

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


	public void updateInfo (Long id, UpdateUserDto updateUserDto) {
		User user = userRepository.findById(id).get();

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
		if (passwordEncoder.matches(oldPassword, userRepository.getById(id).getPassword()))
			return true;
		return false;
	}

	public void deleteUser (Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Error: No user found."));

		List<Order> orders = orderRepository.findByUser(user);
		if (!orders.isEmpty()) {
			for (Order order : orders) {
				order.setUser(null);
				order.setAddress(null);
			}
			orderRepository.saveAllAndFlush(orders);
		}

		List<Address> addresses = addressRepository.findByUser(user);
		if (!addresses.isEmpty()) {
			addressRepository.deleteAllInBatch(addresses);
		}

		List<Recommendation> recommendations = recommendationRepository.findByUser(user);
		if (!recommendations.isEmpty()) {
			recommendationRepository.deleteAllInBatch(recommendations);
		}

		List<Review> reviews = reviewRepository.findByUser(user);
		if (!reviews.isEmpty()) {
			for (Review review  : reviews) {
				review.setUser(null);
			}
			reviewRepository.saveAllAndFlush(reviews);
		}
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
