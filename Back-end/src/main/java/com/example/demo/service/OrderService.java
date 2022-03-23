package com.example.demo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sound.midi.SysexMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.EOrderStatus;
import com.example.demo.common.EPayment;
import com.example.demo.common.EPaymentStatus;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.Product;
import com.example.demo.payload.OrderDto;
import com.example.demo.payload.OrderItemDto;
import com.example.demo.payload.OrderStatusDto;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VoucherRepository;
import com.example.demo.repository.ProductSKURepository;
import com.example.demo.repository.ProductRepository;

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

	@Autowired
	ProductSKURepository productSKURepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressService addressService;

	public List<OrderDto> getOrderList() {
		List<Order> orderList = orderRepository.findAll();
		if (orderList.isEmpty()) {
			return null;
		}

		List<OrderDto> orderListDto = new ArrayList<>();

		for (Order order : orderList) {
			OrderDto orderDto = new OrderDto();
			List<OrderItemDto> orderItemList = new ArrayList<>();

			orderDto.setId(order.getId());
			orderDto.setOrderStatus(order.getOrderStatus().toString());
			orderDto.setUsername(order.getUser().getUsername());
			orderDto.setPaymentStatus(order.getPaymentStatus().toString());
			orderDto.setPayment(order.getPayment().getName().toString());
			Set<OrderItem> orderItems = order.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				OrderItemDto orderItemDto = new OrderItemDto();
				orderItemDto.setOrderId(orderItem.getOrder().getId());
				orderItemDto.setProductSKUId(orderItem.getProductSKU().getId());
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

			orderListDto.add(orderDto);
		}
		return orderListDto;
	}

	public List<OrderDto> getOrderListByUser(String username) {
		List<Order> orderList = orderRepository.findByUser(userRepository.findByUsername(username).get());
		if (orderList.isEmpty()) {
			return null;
		}

		List<OrderDto> orderListDto = new ArrayList<>();

		for (Order order : orderList) {
			OrderDto orderDto = new OrderDto();
			List<OrderItemDto> orderItemList = new ArrayList<>();

			orderDto.setId(order.getId());
			orderDto.setOrderStatus(order.getOrderStatus().toString());
			orderDto.setPaymentStatus(order.getPaymentStatus().toString());
			orderDto.setPayment(order.getPayment().getName().toString());
			Set<OrderItem> orderItems = order.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				OrderItemDto orderItemDto = new OrderItemDto();
				orderItemDto.setOrderId(orderItem.getOrder().getId());
				orderItemDto.setProductSKUId(orderItem.getProductSKU().getId());
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

			orderListDto.add(orderDto);
		}
		return orderListDto;
	}

	public OrderDto getOrderById (Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Error: No object found."));

		List<OrderItemDto> orderItemList = new ArrayList<>();
		OrderDto orderDto = new OrderDto();


		orderDto.setId(order.getId());
		orderDto.setOrderStatus(order.getOrderStatus().toString());
		orderDto.setUsername(order.getUser().getUsername());
		orderDto.setPaymentStatus(order.getPaymentStatus().toString());
		orderDto.setPayment(order.getPayment().getName().toString());
		Set<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			OrderItemDto orderItemDto = new OrderItemDto();
			orderItemDto.setOrderId(id);
			orderItemDto.setProductSKUId(orderItem.getProductSKU().getId());
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

		if (orderDto.getPayment().equalsIgnoreCase(EPayment.COD.toString())) {
			order.setOrderStatus(EOrderStatus.PROCCESSING);
			order.setPayment(paymentRepository.findByName(EPayment.COD).get());
		} else if (orderDto.getPayment().equalsIgnoreCase(EPayment.VNPAY.toString())) {
			order.setOrderStatus(EOrderStatus.PENDING);
			order.setPayment(paymentRepository.findByName(EPayment.VNPAY).get());
		}
		order.setUser(userRepository.findByUsername(orderDto.getUsername()).get());
		order.setPaymentStatus(EPaymentStatus.PENDING);

		order.setSubTotal(orderDto.getSubTotal());
		if (orderDto.getVoucherCode() != null)
			order.setVoucher(voucherRepository.findByCode(orderDto.getVoucherCode()).get());
		order.setDeliveryFeeTotal(orderDto.getDeliveryFeeTotal());
		order.setPaymentTotal(orderDto.getPaymentTotal());
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setPaymentDate(null);
		order.setAddress(addressRepository.findById(orderDto.getAddressDto().getId()).get());

		orderRepository.saveAndFlush(order);
		for (OrderItemDto orderItemDto : orderDto.getOrderItemDtos()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(orderRepository.findById(order.getId()).get());
			Product_SKU productSKU = productSKURepository.findById(orderItemDto.getProductSKUId()).get();
			if(order.getOrderStatus().equals(EOrderStatus.PROCCESSING.toString())) {
				Product product = productSKU.getProducts();
				product.setSold(product.getSold() + orderItemDto.getQuantity());
			}
			productSKU.setStock(productSKU.getStock()-orderItemDto.getQuantity());
			productSKURepository.save(productSKU);
			orderItem.setProductSKU(productSKU);
			orderItem.setQuantity(orderItemDto.getQuantity());
			orderItem.setPrice(orderItemDto.getPrice());
			orderItemList.add(orderItem);
			orderItemRepository.saveAndFlush(orderItem);
		}
		order.setOrderItems(orderItemList);
		orderRepository.saveAndFlush(order);

		return getOrderById(order.getId());
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
			for (OrderItem orderItem : order.getOrderItems()) {
				Product product = orderItem.getProductSKU().getProducts();
				product.setSold(product.getSold() + orderItem.getQuantity());
				productRepository.save(product);
			}
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
			order.setPaymentDate(Calendar.getInstance().getTime());
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
			Product_SKU productSKU = productSKURepository.findById(orderItem.getProductSKU().getId()).get();
			productSKU.setStock(productSKU.getStock()+orderItem.getQuantity());
			productSKURepository.save(productSKU);
		}
		orderRepository.deleteById(id);
	}
}
