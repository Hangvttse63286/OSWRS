package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.common.ERole;
import com.example.demo.entity.Address;
import com.example.demo.entity.Order;
import com.example.demo.entity.Recommendation;
import com.example.demo.entity.Review;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.payload.ProductIncludeImageDTO;
import com.example.demo.payload.RoleChangeDto;
import com.example.demo.payload.UpdateUserDto;
import com.example.demo.payload.UserDto;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.ReviewRepository;

@Service
@Transactional
public class AccountService {

	@Autowired
    private UserRepository userRepository;

	@Autowired
    private RoleRepository roleRepository;

	@Autowired
    private OrderRepository orderRepository;

	@Autowired
    private ReviewRepository reviewRepository;

	@Autowired
    private RecommendationRepository recommendationRepository;

	@Autowired
    private AddressRepository addressRepository;

	public List<UserDto> findAll () {
		List<User> userList = userRepository.findAll();
		if (userList.isEmpty())
			return new ArrayList<UserDto>();
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

	public UserDto findByUsername (String username) {
			User user = userRepository.findByUsername(username)
					.orElseThrow(() -> new NullPointerException("Error: No user found."));

			UserDto userDto = new UserDto();
			userDto.setFirst_name(user.getFirst_name());
			userDto.setLast_name(user.getLast_name());
			userDto.setEmail(user.getEmail());
			userDto.setUsername(user.getUsername());
			userDto.setPhone_number(user.getPhone_number());
			userDto.setGender_id(user.getGender_id());
			userDto.setBirthday(user.getBirthday());
			List<String> roles = user.getRoles().stream().map(item -> item.getName().toString())
					.collect(Collectors.toList());
			userDto.setRoles(roles);

			return userDto;

	}

	public String changeRole (String username, RoleChangeDto roleChangeDto) {
			User user = userRepository.findByUsername(username)
					.orElseThrow(() -> new NullPointerException("Error: No user found."));

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
				default:
					throw new RuntimeException("Error: Role not found: " + role);
				}
			});

			user.setRoles(roles);
			userRepository.save(user);
			return "Change roles successfully!";

	}


	public String deleteAcc (String username) {
		User user = userRepository.findByUsername(username)
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

		userRepository.deleteByUsername(username);
		return "Delete successfully!";
	}

}
