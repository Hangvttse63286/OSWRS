package com.example.demo.payload;

public class OrderStatusDto {
	private String orderStatus;
	private String paymentStatus;

	public OrderStatusDto(String orderStatus, String paymentStatus) {
		super();
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
