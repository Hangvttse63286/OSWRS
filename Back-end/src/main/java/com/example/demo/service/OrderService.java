package com.example.demo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.EOrderStatus;
import com.example.demo.common.EPayment;
import com.example.demo.common.EPaymentStatus;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product_SKU;
import com.example.demo.payload.OrderDto;
import com.example.demo.payload.OrderItemDto;
import com.example.demo.payload.OrderStatusDto;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VoucherRepository;

@Service
@Transactional
public class OrderService {
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	VoucherRepository voucherRepository;

	@Autowired
	AddressRepository addressRepository;

//	@Autowired
//	Product_SKURepository productSKURepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressService addressService;

	public List<OrderDto> getOrderList() {
		if (orderRepository.findAll() == null) {
			return null;
		}

		List<OrderDto> orderList = new ArrayList<>();
		List<OrderItemDto> orderItemList = new ArrayList<>();

		OrderDto orderDto = new OrderDto();
		OrderItemDto orderItemDto = new OrderItemDto();

		for (Order order : orderRepository.findAll()) {
			orderDto.setId(order.getId());
			orderDto.setOrderStatus(order.getOrderStatus().toString());
			orderDto.setUsername(order.getUser().getUsername());
			orderDto.setPaymentStatus(order.getPaymentStatus().toString());
			orderDto.setPayment(order.getPayment().getName().toString());
			for (OrderItem orderItem : order.getOrderItems()) {
				orderItemDto.setOrderId(orderItem.getOrder().getId());
				orderItemDto.setProductSKUId(orderItem.getProductSKU().getProduct_sku_id());
				orderItemDto.setQuantity(orderItem.getQuantity());
				orderItemDto.setPrice(orderItem.getPrice());
				orderItemList.add(orderItemDto);
			}
			orderDto.setOrderItemDtos(orderItemList);
			orderDto.setSubTotal(order.getSubTotal());
			if (order.getVoucher() != null)
				orderDto.setVoucherCode(order.getVoucher().getCode());
			orderDto.setDeliveryFeeTotal(order.getDeliveryFeeTotal());
			orderDto.setPaymentTotal(order.getPaymentTotal());
			orderDto.setOrderDate(order.getOrderDate());
			if (order.getPaymentDate() != null)
				orderDto.setPaymentDate(order.getPaymentDate());
			orderDto.setAddressDto(addressService.getAddressById(order.getAddress().getId()));

			orderList.add(orderDto);
		}
		return orderList;
	}

	public List<OrderDto> getOrderListByUser(String username) {
		if (orderRepository.findByUser(userRepository.findByUsername(username).get()) == null) {
			return null;
		}

		List<OrderDto> orderList = new ArrayList<>();
		List<OrderItemDto> orderItemList = new ArrayList<>();

		OrderDto orderDto = new OrderDto();
		OrderItemDto orderItemDto = new OrderItemDto();

		for (Order order : orderRepository.findByUser(userRepository.findByUsername(username).get())) {
			orderDto.setId(order.getId());
			orderDto.setOrderStatus(order.getOrderStatus().toString());
			orderDto.setPaymentStatus(order.getPaymentStatus().toString());
			orderDto.setPayment(order.getPayment().getName().toString());
			for (OrderItem orderItem : order.getOrderItems()) {
				orderItemDto.setOrderId(orderItem.getOrder().getId());
				orderItemDto.setProductSKUId(orderItem.getProductSKU().getProduct_sku_id());
				orderItemDto.setQuantity(orderItem.getQuantity());
				orderItemDto.setPrice(orderItem.getPrice());
				orderItemList.add(orderItemDto);
			}
			orderDto.setOrderItemDtos(orderItemList);
			orderDto.setSubTotal(order.getSubTotal());
			if (order.getVoucher() != null)
				orderDto.setVoucherCode(order.getVoucher().getCode());
			orderDto.setDeliveryFeeTotal(order.getDeliveryFeeTotal());
			orderDto.setPaymentTotal(order.getPaymentTotal());
			orderDto.setOrderDate(order.getOrderDate());
			if (order.getPaymentDate() != null)
				orderDto.setPaymentDate(order.getPaymentDate());
			orderDto.setAddressDto(addressService.getAddressById(order.getAddress().getId()));

			orderList.add(orderDto);
		}
		return orderList;
	}

	public OrderDto getOrderById (Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Error: No object found."));

		List<OrderItemDto> orderItemList = new ArrayList<>();
		OrderDto orderDto = new OrderDto();
		OrderItemDto orderItemDto = new OrderItemDto();

		orderDto.setId(order.getId());
		orderDto.setOrderStatus(order.getOrderStatus().toString());
		orderDto.setUsername(order.getUser().getUsername());
		orderDto.setPaymentStatus(order.getPaymentStatus().toString());
		orderDto.setPayment(order.getPayment().getName().toString());
		for (OrderItem orderItem : order.getOrderItems()) {
			orderItemDto.setOrderId(orderItem.getOrder().getId());
			orderItemDto.setProductSKUId(orderItem.getProductSKU().getProduct_sku_id());
			orderItemDto.setQuantity(orderItem.getQuantity());
			orderItemDto.setPrice(orderItem.getPrice());
			orderItemList.add(orderItemDto);
		}
		orderDto.setOrderItemDtos(orderItemList);
		orderDto.setSubTotal(order.getSubTotal());
		if (order.getVoucher() != null)
			orderDto.setVoucherCode(order.getVoucher().getCode());
		orderDto.setDeliveryFeeTotal(order.getDeliveryFeeTotal());
		orderDto.setPaymentTotal(order.getPaymentTotal());
		orderDto.setOrderDate(order.getOrderDate());
		if (order.getPaymentDate() != null)
			orderDto.setPaymentDate(order.getPaymentDate());
		orderDto.setAddressDto(addressService.getAddressById(order.getAddress().getId()));

		return orderDto;
	}

	public OrderDto createOrder(OrderDto orderDto) {
		Set<OrderItem> orderItemList = new HashSet<>();

		Order order = new Order();
		OrderItem orderItem = new OrderItem();

		if (orderDto.getPayment().equalsIgnoreCase(EPayment.COD.toString())) {
			order.setOrderStatus(EOrderStatus.PROCCESSING);
			order.setPayment(paymentRepository.findByName(EPayment.COD).get());
		} else if (orderDto.getPayment().equalsIgnoreCase(EPayment.VNPAY.toString())) {
			order.setOrderStatus(EOrderStatus.PENDING);
			order.setPayment(paymentRepository.findByName(EPayment.VNPAY).get());
		}
		order.setUser(userRepository.findByUsername(orderDto.getUsername()).get());
		order.setPaymentStatus(EPaymentStatus.PENDING);
		for (OrderItemDto orderItemDto : orderDto.getOrderItemDtos()) {
			orderItem.setOrder(orderRepository.findById(orderItemDto.getOrderId()).get());
//			Product_SKU productSKU = productSKURepository.findById(orderItemDto.getProductSKUId()).get();
//			productSKU.setStock(productSKU.getStock()-orderItemDto.getQuantity());
//			productSKURepository.save(productSKU);
//			orderItem.setProductSKU(productSKU);
			orderItem.setQuantity(orderItemDto.getQuantity());
			orderItem.setPrice(orderItemDto.getPrice());
			orderItemList.add(orderItem);
		}
		order.setOrderItems(orderItemList);
		order.setSubTotal(orderDto.getSubTotal());
		if (orderDto.getVoucherCode() != null)
			order.setVoucher(voucherRepository.findByCode(orderDto.getVoucherCode()).get());
		order.setDeliveryFeeTotal(orderDto.getDeliveryFeeTotal());
		order.setPaymentTotal(orderDto.getPaymentTotal());
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setPaymentDate(null);
		order.setAddress(addressRepository.findById(orderDto.getAddressDto().getId()).get());

		orderRepository.save(order);

		return getOrderById(orderRepository.findLatestByUserId(order.getUser().getId()).get().getId());
	}

	public OrderDto changeOrderStatus (Long id, OrderStatusDto orderStatusDto) {
		Order order = orderRepository.findById(id).get();
		if (order == null)
			return null;
		switch (orderStatusDto.getOrderStatus()) {
		case "INCOMPLETE":
			order.setOrderStatus(EOrderStatus.INCOMPLETE);
			break;
		case "CANCELLED":
			order.setOrderStatus(EOrderStatus.CANCELLED);
			break;
		case "DECLINED":
			order.setOrderStatus(EOrderStatus.DECLINED);
			break;
		case "PENDING":
			order.setOrderStatus(EOrderStatus.PENDING);
			break;
		case "PROCCESSING":
			order.setOrderStatus(EOrderStatus.PROCCESSING);
			break;
		case "COMPLETED":
			order.setOrderStatus(EOrderStatus.COMPLETED);
			break;
		}
		switch (orderStatusDto.getPaymentStatus()) {
		case "AWAITING_REFUND":
			order.setPaymentStatus(EPaymentStatus.AWAITING_REFUND);
			break;
		case "DENIED":
			order.setPaymentStatus(EPaymentStatus.DENIED);
			break;
		case "FAILED":
			order.setPaymentStatus(EPaymentStatus.FAILED);
			break;
		case "PENDING":
			order.setPaymentStatus(EPaymentStatus.PENDING);
			break;
		case "REFUNDED":
			order.setPaymentStatus(EPaymentStatus.REFUNDED);
			break;
		case "COMPLETED":
			order.setPaymentStatus(EPaymentStatus.COMPLETED);
			break;
		}
		orderRepository.save(order);
		return getOrderById(id);
	}

	public OrderDto UpdateOrder(Long id, OrderDto orderDto) {
		Set<OrderItem> orderItemList = new HashSet<>();

		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Error: No object found."));
//		OrderItem orderItem = new OrderItem();
		switch (orderDto.getOrderStatus()) {
		case "INCOMPLETE":
			order.setOrderStatus(EOrderStatus.INCOMPLETE);
			break;
		case "CANCELLED":
			order.setOrderStatus(EOrderStatus.CANCELLED);
			break;
		case "DECLINED":
			order.setOrderStatus(EOrderStatus.DECLINED);
			break;
		case "PENDING":
			order.setOrderStatus(EOrderStatus.PENDING);
			break;
		case "PROCCESSING":
			order.setOrderStatus(EOrderStatus.PROCCESSING);
			break;
		case "COMPLETED":
			order.setOrderStatus(EOrderStatus.COMPLETED);
			break;
		}
		switch (orderDto.getPaymentStatus()) {
		case "AWAITING_REFUND":
			order.setPaymentStatus(EPaymentStatus.AWAITING_REFUND);
			break;
		case "DENIED":
			order.setPaymentStatus(EPaymentStatus.DENIED);
			break;
		case "FAILED":
			order.setPaymentStatus(EPaymentStatus.FAILED);
			break;
		case "PENDING":
			order.setPaymentStatus(EPaymentStatus.PENDING);
			break;
		case "REFUNDED":
			order.setPaymentStatus(EPaymentStatus.REFUNDED);
			break;
		case "COMPLETED":
			order.setPaymentStatus(EPaymentStatus.COMPLETED);
			break;
		}
//		for (OrderItemDto orderItemDto : orderDto.getOrderItemDtos()) {
//			Product_SKU productSKU = productSKURepository.findById(orderItemDto.getProductSKUId()).get();
//			productSKU.setStock(productSKU.getStock() - (orderItemRepository.findByOrderAndProductSKU(orderItemDto.getOrderId(),orderItemDto.getProductSKUId()) - orderItemDto.getQuantity()));
//			productSKURepository.save(productSKU);
//			orderItem.setProductSKU(productSKU);
//			orderItem.setQuantity(orderItemDto.getQuantity());
//			orderItem.setPrice(orderItemDto.getPrice());
//			orderItemList.add(orderItem);
//		}
//		order.setOrderItems(orderItemList);
//		order.setSubTotal(orderDto.getSubTotal());
//		if (orderDto.getVoucherCode() != null)
//			order.setVoucher(voucherRepository.findByCode(orderDto.getVoucherCode()).get());
//		order.setDeliveryFeeTotal(orderDto.getDeliveryFeeTotal());
//		order.setPaymentTotal(orderDto.getPaymentTotal());
		if (orderDto.getPaymentDate() != null)
			order.setPaymentDate(orderDto.getPaymentDate());

		orderRepository.save(order);

		return getOrderById(id);
	}

	public void deleteOrder (Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Error: No object found."));
		for (OrderItem orderItem : order.getOrderItems()) {
//			Product_SKU productSKU = productSKURepository.findById(orderItem.getProductSKU().getId()).get();
//			productSKU.setStock(productSKU.getStock()+orderItem.getQuantity());
//			productSKURepository.save(productSKU);
		}
		orderRepository.deleteById(id);
	}
}
