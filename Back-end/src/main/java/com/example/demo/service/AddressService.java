package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Address;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.payload.AddressDto;
import com.example.demo.payload.UserDto;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepository;

	@Autowired
    private UserRepository userRepository;

	@Autowired
    private OrderRepository orderRepository;

	public List<AddressDto> getAddressList (String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NullPointerException("Error: No user found."));
		List<AddressDto> addressListDto = new ArrayList<>();
		List<Address> addressList = addressRepository.findByUser(user);
		if (addressList.isEmpty())
			return new ArrayList<AddressDto>();
		for (Address address : addressList) {
			AddressDto addressDto = new AddressDto();
			addressDto.setId(address.getId());
			addressDto.setReceiverName(address.getReceiverName());
			addressDto.setProvince(address.getProvince());
			addressDto.setCity(address.getCity());
			addressDto.setDistrict(address.getDistrict());
			addressDto.setSubDistrict(address.getSubDistrict());
			addressDto.setStreet(address.getStreet());
			addressDto.setPostalCode(address.getPostalCode());
			addressDto.setPhoneNumber(address.getPhoneNumber());

			addressListDto.add(addressDto);
		}
		return addressListDto;
	}

	public AddressDto getAddressById (Long id) {
			Address address = addressRepository.findById(id)
					.orElseThrow(() -> new NullPointerException("Error: No address found."));

			AddressDto addressDto = new AddressDto();
			addressDto.setId(address.getId());
			addressDto.setReceiverName(address.getReceiverName());
			addressDto.setProvince(address.getProvince());
			addressDto.setCity(address.getCity());
			addressDto.setDistrict(address.getDistrict());
			addressDto.setSubDistrict(address.getSubDistrict());
			addressDto.setStreet(address.getStreet());
			addressDto.setPostalCode(address.getPostalCode());
			addressDto.setPhoneNumber(address.getPhoneNumber());

			return addressDto;

	}

	public AddressDto getAddressDto (Address address) {
		AddressDto addressDto = new AddressDto();
		addressDto.setId(address.getId());
		addressDto.setReceiverName(address.getReceiverName());
		addressDto.setProvince(address.getProvince());
		addressDto.setCity(address.getCity());
		addressDto.setDistrict(address.getDistrict());
		addressDto.setSubDistrict(address.getSubDistrict());
		addressDto.setStreet(address.getStreet());
		addressDto.setPostalCode(address.getPostalCode());
		addressDto.setPhoneNumber(address.getPhoneNumber());

		return addressDto;
	}

	public AddressDto updateAddress (Long id, AddressDto addressDto) {
			Address address = addressRepository.findById(id)
					.orElseThrow(() -> new NullPointerException("Error: No address found."));

			address.setReceiverName(addressDto.getReceiverName());
			address.setProvince(addressDto.getProvince());
			address.setCity(addressDto.getCity());
			address.setDistrict(addressDto.getDistrict());
			address.setSubDistrict(addressDto.getSubDistrict());
			address.setStreet(addressDto.getStreet());
			address.setPostalCode(addressDto.getPostalCode());
			address.setPhoneNumber(addressDto.getPhoneNumber());
			addressRepository.save(address);

			return getAddressDto(address);
	}

	public AddressDto createAddress (AddressDto addressDto, String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NullPointerException("Error: No user found."));
		Address address = new Address();

		address.setUser(user);
		address.setReceiverName(addressDto.getReceiverName());
		address.setProvince(addressDto.getProvince());
		address.setCity(addressDto.getCity());
		address.setDistrict(addressDto.getDistrict());
		address.setSubDistrict(addressDto.getSubDistrict());
		address.setStreet(addressDto.getStreet());
		address.setPostalCode(addressDto.getPostalCode());
		address.setPhoneNumber(addressDto.getPhoneNumber());
		addressRepository.saveAndFlush(address);

		return getAddressDto(address);
	}

	public void deleteAddressById (Long id) {
		Address address = addressRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Error: No address found."));
		List<Order> orders = orderRepository.findByAddress(address);
		if (!orders.isEmpty()) {
			for (Order order : orders) {
				order.setAddress(null);
			}
			orderRepository.saveAllAndFlush(orders);
		}
		addressRepository.deleteById(id);
	}
}
