package com.example.demo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.example.demo.entity.Address;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.User;
import com.example.demo.entity.Product;
import com.example.demo.payload.MonthStatistic;
import com.example.demo.payload.OrderDto;
import com.example.demo.payload.OrderItemDto;
import com.example.demo.payload.OrderItemUserDto;
import com.example.demo.payload.OrderStatusDto;
import com.example.demo.payload.OrderUserDto;
import com.example.demo.payload.ProductIncludeImageDTO;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ReviewRepository;
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
	CartRepository cartRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	AddressService addressService;

	public List<OrderDto> getOrderList() {
		List<Order> orderList = orderRepository.findAllByOrderByOrderDateDesc();
		if (orderList.isEmpty()) {
			return null;
		}

		List<OrderDto> orderListDto = new ArrayList<>();

		for (Order order : orderList) {
			OrderDto orderDto = new OrderDto();
			List<OrderItemDto> orderItemList = new ArrayList<>();

			orderDto.setId(order.getId());
			orderDto.setOrderStatus(order.getOrderStatus().toString());
			User user = order.getUser();
			if (user != null)
				orderDto.setUsername(user.getUsername());
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
			Address address = order.getAddress();
			if (address != null)
				orderDto.setAddressId(address.getId());

			orderListDto.add(orderDto);
		}
		return orderListDto;
	}

	public List<OrderUserDto> getOrderListByUser(String username) {
		List<Order> orderList = orderRepository
				.findByUserOrderByOrderDateDesc(userRepository.findByUsername(username).get());
		if (orderList.isEmpty()) {
			return new ArrayList<>();
		}

		List<OrderUserDto> orderListDto = new ArrayList<>();

		for (Order order : orderList) {
			OrderUserDto orderDto = new OrderUserDto();
			List<OrderItemUserDto> orderItemList = new ArrayList<>();

			orderDto.setId(order.getId());
			orderDto.setOrderStatus(order.getOrderStatus().toString());
			orderDto.setPaymentStatus(order.getPaymentStatus().toString());
			orderDto.setPayment(order.getPayment().getName().toString());
			Set<OrderItem> orderItems = order.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				OrderItemUserDto orderItemDto = new OrderItemUserDto();
				orderItemDto.setOrderId(orderItem.getOrder().getId());
				orderItemDto.setProductSKUId(orderItem.getProductSKU().getId());
				orderItemDto.setQuantity(orderItem.getQuantity());
				orderItemDto.setPrice(orderItem.getPrice());
				if (reviewRepository.existsByUserAndOrderAndProducts(order.getUser(), order,
						orderItem.getProductSKU().getProducts()))
					orderItemDto.setReview(true);
				else
					orderItemDto.setReview(false);
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
			Address address = order.getAddress();
			if (address != null)
				orderDto.setAddressId(address.getId());

			orderListDto.add(orderDto);
		}
		return orderListDto;
	}

	public OrderDto getOrderById(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Error: No order found."));

		List<OrderItemDto> orderItemList = new ArrayList<>();
		OrderDto orderDto = new OrderDto();

		orderDto.setId(order.getId());
		orderDto.setOrderStatus(order.getOrderStatus().toString());
		User user = order.getUser();
		if (user != null)
			orderDto.setUsername(user.getUsername());
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
		Address address = order.getAddress();
		if (address != null)
			orderDto.setAddressId(address.getId());

		return orderDto;
	}

	public OrderDto getOrderByIdAndUser(Long id, String username) {
		User user = userRepository.findByUsername(username).get();
		Order order = orderRepository.findByIdAndUser(id, user)
				.orElseThrow(() -> new NullPointerException("Error: No order found."));

		List<OrderItemDto> orderItemList = new ArrayList<>();
		OrderDto orderDto = new OrderDto();

		orderDto.setId(order.getId());
		orderDto.setOrderStatus(order.getOrderStatus().toString());
		orderDto.setUsername(user.getUsername());
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
		Address address = order.getAddress();
		if (address != null)
			orderDto.setAddressId(address.getId());

		return orderDto;
	}

	public OrderDto getOrderDto(Order order) {

		List<OrderItemDto> orderItemList = new ArrayList<>();
		OrderDto orderDto = new OrderDto();

		orderDto.setId(order.getId());
		orderDto.setOrderStatus(order.getOrderStatus().toString());
		User user = order.getUser();
		if (user != null)
			orderDto.setUsername(user.getUsername());
		orderDto.setUsername(order.getUser().getUsername());
		orderDto.setPaymentStatus(order.getPaymentStatus().toString());
		orderDto.setPayment(order.getPayment().getName().toString());
		Set<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			OrderItemDto orderItemDto = new OrderItemDto();
			orderItemDto.setOrderId(order.getId());
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
		Address address = order.getAddress();
		if (address != null)
			orderDto.setAddressId(address.getId());

		return orderDto;
	}

	public OrderDto createOrder(OrderDto orderDto, String username) {
		Set<OrderItem> orderItemList = new HashSet<>();

		Order order = new Order();
		User user = userRepository.findByUsername(orderDto.getUsername()).get();
		if (orderDto.getPayment().equalsIgnoreCase(EPayment.COD.toString())) {
			order.setPayment(paymentRepository.findByName(EPayment.COD).get());
			Cart cart = cartRepository.findByUser(user).get();
			cart.setCartItems(null);
			cartRepository.saveAndFlush(cart);
		} else if (orderDto.getPayment().equalsIgnoreCase(EPayment.VNPAY.toString())) {
			order.setPayment(paymentRepository.findByName(EPayment.VNPAY).get());
		}
		order.setOrderStatus(EOrderStatus.PENDING);
		order.setUser(user);
		order.setPaymentStatus(EPaymentStatus.PENDING);

		order.setSubTotal(orderDto.getSubTotal());
		if (orderDto.getVoucherCode() != null)
			order.setVoucher(voucherRepository.findByCode(orderDto.getVoucherCode()).get());
		order.setDeliveryFeeTotal(orderDto.getDeliveryFeeTotal());
		order.setPaymentTotal(orderDto.getPaymentTotal());
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setPaymentDate(null);
		order.setAddress(addressRepository.findById(orderDto.getAddressId()).get());

		orderRepository.saveAndFlush(order);

		for (OrderItemDto orderItemDto : orderDto.getOrderItemDtos()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(orderRepository.findById(order.getId()).get());
			Product_SKU productSKU = productSKURepository.findById(orderItemDto.getProductSKUId()).get();
			if (productSKU.getStock() < orderItemDto.getQuantity())
				return null;
			productSKU.setStock(productSKU.getStock() - orderItemDto.getQuantity());
			productSKURepository.save(productSKU);
			orderItem.setProductSKU(productSKU);
			orderItem.setQuantity(orderItemDto.getQuantity());
			orderItem.setPrice(orderItemDto.getPrice());
			orderItemList.add(orderItem);
			orderItemRepository.saveAndFlush(orderItem);
		}
		order.setOrderItems(orderItemList);
		orderRepository.saveAndFlush(order);

		return getOrderDto(order);
	}

	public OrderDto changeOrderStatus(Long id, OrderStatusDto orderStatusDto) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NullPointerException("Error: No order found."));
		boolean flag = false;

		if (orderStatusDto.getOrderStatus() != null && !orderStatusDto.getOrderStatus().trim().isEmpty()) {
			switch (orderStatusDto.getOrderStatus()) {
			case "UNSUCCESSFUL":
				order.setOrderStatus(EOrderStatus.UNSUCCESSFUL);
				Set<OrderItem> orderItems = order.getOrderItems();
				for (OrderItem orderItem : orderItems) {
					Product_SKU productSKU = orderItem.getProductSKU();
					productSKU.setStock(productSKU.getStock() + orderItem.getQuantity());
				}
				break;
			case "PENDING":
				order.setOrderStatus(EOrderStatus.PENDING);
				break;
			case "CONFIRMED":
				order.setOrderStatus(EOrderStatus.CONFIRMED);
				break;
			case "SUCCESSFUL":
				order.setOrderStatus(EOrderStatus.SUCCESSFUL);
				for (OrderItem orderItem : order.getOrderItems()) {
					Product product = orderItem.getProductSKU().getProducts();
					product.setSold(product.getSold() + orderItem.getQuantity());
					productRepository.save(product);
				}
				break;
			}
			flag = true;
		}

		if (orderStatusDto.getPaymentStatus() != null && !orderStatusDto.getPaymentStatus().trim().isEmpty()) {
			switch (orderStatusDto.getPaymentStatus()) {
			case "UNSUCCESSFUL":
				order.setPaymentStatus(EPaymentStatus.UNSUCCESSFUL);
				break;
			case "PENDING":
				order.setPaymentStatus(EPaymentStatus.PENDING);
				break;
			case "SUCCESSFUL":
				order.setPaymentStatus(EPaymentStatus.SUCCESSFUL);
				order.setPaymentDate(Calendar.getInstance().getTime());
				break;
			}
			flag = true;
		}

		if (flag)
			orderRepository.save(order);
		return getOrderDto(order);

	}

//	public OrderDto UpdateOrder(Long id, OrderDto orderDto) {
//		Set<OrderItem> orderItemList = new HashSet<>();
//
//		Order order = orderRepository.findById(id)
//				.orElseThrow(() -> new NullPointerException("Error: No object found."));
////		OrderItem orderItem = new OrderItem();
//		switch (orderDto.getOrderStatus()) {
//		case "INCOMPLETE":
//			order.setOrderStatus(EOrderStatus.INCOMPLETE);
//			break;
//		case "CANCELLED":
//			order.setOrderStatus(EOrderStatus.CANCELLED);
//			break;
//		case "DECLINED":
//			order.setOrderStatus(EOrderStatus.DECLINED);
//			break;
//		case "PENDING":
//			order.setOrderStatus(EOrderStatus.PENDING);
//			break;
//		case "PROCCESSING":
//			order.setOrderStatus(EOrderStatus.PROCCESSING);
//			break;
//		case "COMPLETED":
//			order.setOrderStatus(EOrderStatus.COMPLETED);
//			break;
//		}
//		switch (orderDto.getPaymentStatus()) {
//		case "AWAITING_REFUND":
//			order.setPaymentStatus(EPaymentStatus.AWAITING_REFUND);
//			break;
//		case "DENIED":
//			order.setPaymentStatus(EPaymentStatus.DENIED);
//			break;
//		case "FAILED":
//			order.setPaymentStatus(EPaymentStatus.FAILED);
//			break;
//		case "PENDING":
//			order.setPaymentStatus(EPaymentStatus.PENDING);
//			break;
//		case "REFUNDED":
//			order.setPaymentStatus(EPaymentStatus.REFUNDED);
//			break;
//		case "COMPLETED":
//			order.setPaymentStatus(EPaymentStatus.COMPLETED);
//			break;
//		}
////		for (OrderItemDto orderItemDto : orderDto.getOrderItemDtos()) {
////			Product_SKU productSKU = productSKURepository.findById(orderItemDto.getProductSKUId()).get();
////			productSKU.setStock(productSKU.getStock() - (orderItemRepository.findByOrderAndProductSKU(orderItemDto.getOrderId(),orderItemDto.getProductSKUId()) - orderItemDto.getQuantity()));
////			productSKURepository.save(productSKU);
////			orderItem.setProductSKU(productSKU);
////			orderItem.setQuantity(orderItemDto.getQuantity());
////			orderItem.setPrice(orderItemDto.getPrice());
////			orderItemList.add(orderItem);
////		}
////		order.setOrderItems(orderItemList);
////		order.setSubTotal(orderDto.getSubTotal());
////		if (orderDto.getVoucherCode() != null)
////			order.setVoucher(voucherRepository.findByCode(orderDto.getVoucherCode()).get());
////		order.setDeliveryFeeTotal(orderDto.getDeliveryFeeTotal());
////		order.setPaymentTotal(orderDto.getPaymentTotal());
//		if (orderDto.getPaymentDate() != null)
//			order.setPaymentDate(orderDto.getPaymentDate());
//
//		orderRepository.save(order);
//
//		return getOrderDto(order);
//	}

	public void deleteOrder(Long id, String username) {
		User user = userRepository.findByUsername(username).get();
		Order order = orderRepository.findByIdAndUser(id, user)
				.orElseThrow(() -> new NullPointerException("Error: No order found."));
		Set<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			Product_SKU productSKU = productSKURepository.findById(orderItem.getProductSKU().getId()).get();
			productSKU.setStock(productSKU.getStock() + orderItem.getQuantity());
			productSKURepository.save(productSKU);
		}
		orderRepository.deleteById(id);
	}

	public boolean existsByIdAndUser(Long id, String username) {
		User user = userRepository.findByUsername(username).get();
		return orderRepository.existsByIdAndUser(id, user);
	}

	public MonthStatistic getStatisticCurrentMonth() {
		List<Order> orderList = orderRepository.findAllByOrderDateMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
		if (orderList.isEmpty()) {
			return new MonthStatistic (0,0,0,0,0,0.0);
		}
		int numOfOrder = orderList.size();
		int numOfSuccessfulOrder = 0;
		int numOfConfirmedOrder = 0;
		int numOfPendingOrder = 0;
		int numOfUnsuccessfulOrder = 0;

		Double totalSale = 0.0;

		for (Order order : orderList) {
//			Date orderDate= order.getOrderDate();
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(orderDate);
//			int month = cal.get(Calendar.MONTH);
//			if (month == Calendar.getInstance().get(Calendar.MONTH)) {
				switch (order.getOrderStatus()) {
				case UNSUCCESSFUL:
					numOfUnsuccessfulOrder++;
					break;
				case CONFIRMED:
					numOfConfirmedOrder++;
					break;
				case PENDING:
					numOfPendingOrder++;
					break;
				case SUCCESSFUL:
					numOfSuccessfulOrder++;
					totalSale += order.getSubTotal();
					break;
				}
//			}
		}

		return new MonthStatistic(numOfOrder,numOfSuccessfulOrder,numOfConfirmedOrder,numOfPendingOrder,numOfUnsuccessfulOrder,totalSale);
	}
}
