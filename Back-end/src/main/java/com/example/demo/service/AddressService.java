package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.payload.AddressDto;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;

@Service
public class AddressService {
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	public List<AddressDto> getAddressList (String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NullPointerException("Error: No object found."));
		List<AddressDto> addressList = new ArrayList<>();
		AddressDto addressDto = new AddressDto();
		
		for (Address address : addressRepository.findByUser(user)) {
			addressDto.setId(address.getId());
			addressDto.setReceiverName(address.getReceiverName());
			addressDto.setProvince(address.getProvince());
			addressDto.setCity(address.getCity());
			addressDto.setDistrict(address.getDistrict());
			addressDto.setSubDistrict(address.getSubDistrict());
			addressDto.setStreet(address.getStreet());
			addressDto.setPostalCode(address.getPostalCode());
			addressDto.setPhoneNumber(address.getPhoneNumber());
			
			addressList.add(addressDto);
		}
		return addressList;
	}
	
	public AddressDto getAddressById (Long id) {
		Address address = addressRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Error: No object found."));
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
				.orElseThrow(() -> new NullPointerException("Error: No object found."));
		
		address.setReceiverName(addressDto.getReceiverName());
		address.setProvince(addressDto.getProvince());
		address.setCity(addressDto.getCity());
		address.setDistrict(addressDto.getDistrict());
		address.setSubDistrict(addressDto.getSubDistrict());
		address.setStreet(addressDto.getStreet());
		address.setPostalCode(addressDto.getPostalCode());
		address.setPhoneNumber(addressDto.getPhoneNumber());
		addressRepository.save(address);
		
		return getAddressById(address.getId());
	}
	
	public AddressDto createAddress (AddressDto addressDto, String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NullPointerException("Error: No object found."));
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
		addressRepository.save(address);
		
		return getAddressById(addressRepository.findLatestByUserId(address.getUser().getId()).get().getId());
	}
	
	public void deleteAddressById (Long id) {
		addressRepository.deleteById(id);
	}
}
