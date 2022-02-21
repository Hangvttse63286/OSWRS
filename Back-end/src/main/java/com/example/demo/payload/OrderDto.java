package com.example.demo.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderDto {
	private long id;
	private String orderStatus;
	private String username;
	private String paymentStatus;
	private String payment;
	private List<OrderItemDto> orderItemDtos = new ArrayList<>();
	private Double subTotal;
	private String voucherCode;
	private Double deliveryFeeTotal;
	private Double paymentTotal;
	private Date orderDate;
	private Date paymentDate;
	private AddressDto addressDto;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public List<OrderItemDto> getOrderItemDtos() {
		return orderItemDtos;
	}
	public void setOrderItemDtos(List<OrderItemDto> orderItemDtos) {
		this.orderItemDtos = orderItemDtos;
	}
	public Double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public Double getPaymentTotal() {
		return paymentTotal;
	}
	public void setPaymentTotal(Double paymentTotal) {
		this.paymentTotal = paymentTotal;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public AddressDto getAddressDto() {
		return addressDto;
	}
	public void setAddressDto(AddressDto addressDto) {
		this.addressDto = addressDto;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Double getDeliveryFeeTotal() {
		return deliveryFeeTotal;
	}
	public void setDeliveryFeeTotal(Double deliveryFeeTotal) {
		this.deliveryFeeTotal = deliveryFeeTotal;
	}


}
