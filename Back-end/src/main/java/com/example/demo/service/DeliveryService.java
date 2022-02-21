//package com.example.demo.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.entity.Delivery;
//import com.example.demo.entity.Order;
//import com.example.demo.payload.DeliveryDto;
//import com.example.demo.repository.DeliveryPartnerRepository;
//import com.example.demo.repository.DeliveryRepository;
//import com.example.demo.repository.OrderRepository;
//import com.example.demo.repository.UserRepository;
//
//@Service
//public class DeliveryService {
//	@Autowired
//	DeliveryRepository deliveryRepository;
//	
//	@Autowired
//	DeliveryPartnerRepository deliveryPartnerRepository;
//	
//	@Autowired
//	OrderRepository orderRepository;
//	
//	@Autowired
//	UserRepository userRepository;
//	
//	@Autowired
//	OrderService orderService;
//	
//	public List<DeliveryDto> getDeliveryList() {
//		if (deliveryRepository.findAll().isEmpty())
//			return null;
//		List<DeliveryDto> deliveryList = new ArrayList<>();
//		DeliveryDto deliveryDto = new DeliveryDto();
//		
//		for (Delivery delivery : deliveryRepository.findAll()) {
//			deliveryDto.setId(delivery.getId());
//			deliveryDto.setOrderDto(orderService.getOrderById(delivery.getOrder().getId()));
//			deliveryDto.setDeliveryPartner(delivery.getDeliveryPartner().getName());
//			deliveryDto.setDeliveryCode(delivery.getDeliveryCode());
//			deliveryDto.setDeliveryStatus(delivery.getDeliveryStatus().toString());
//			deliveryDto.setDeliveryDate(delivery.getDeliveryDate());
//			
//			deliveryList.add(deliveryDto);
//		}
//		return deliveryList;
//	}
//	
//	public List<DeliveryDto> getDeliveryListByUser(String username) {
//		if (orderService.getOrderListByUser(username).isEmpty())
//			return null;
//		List<Delivery> deliveryList = new ArrayList<>();
//		for(Order order : orderRepository.findByUser(userRepository.findByUsername(username).get())) {
//			if (deliveryRepository.findByOrder(order).get() != null)
//				deliveryList.add(deliveryRepository.findByOrder(order).get());
//		}
//		if (deliveryList.isEmpty())
//			return null;
//		
//		List<DeliveryDto> deliveryDtoList = new ArrayList<>();
//		DeliveryDto deliveryDto = new DeliveryDto();
//		
//		for (Delivery delivery : deliveryList) {
//			deliveryDto.setId(delivery.getId());
//			deliveryDto.setOrderDto(orderService.getOrderById(delivery.getOrder().getId()));
//			deliveryDto.setDeliveryPartner(delivery.getDeliveryPartner().getName());
//			deliveryDto.setDeliveryCode(delivery.getDeliveryCode());
//			deliveryDto.setDeliveryStatus(delivery.getDeliveryStatus().toString());
//			deliveryDto.setDeliveryDate(delivery.getDeliveryDate());
//			
//			deliveryDtoList.add(deliveryDto);
//		}
//		return deliveryDtoList;
//	}
//	
//	public DeliveryDto getDeliveryById (Long id) {
//		Delivery delivery = deliveryRepository.findById(id)
//				.orElseThrow(() -> new NullPointerException("Error: No object found."));
//		DeliveryDto deliveryDto = new DeliveryDto();
//		deliveryDto.setId(delivery.getId());
//		deliveryDto.setOrderDto(orderService.getOrderById(delivery.getOrder().getId()));
//		deliveryDto.setDeliveryPartner(delivery.getDeliveryPartner().getName());
//		deliveryDto.setDeliveryCode(delivery.getDeliveryCode());
//		deliveryDto.setDeliveryStatus(delivery.getDeliveryStatus().toString());
//		deliveryDto.setDeliveryDate(delivery.getDeliveryDate());
//		
//		return deliveryDto;
//	}
//	
//	
//}
