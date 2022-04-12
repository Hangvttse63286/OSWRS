package com.example.demo.payload;


public class OrderItemUserDto {
	private Long orderId;
	private Long productSKUId;
	private int quantity;
	private Double price;
	private boolean isReview;

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getProductSKUId() {
		return productSKUId;
	}
	public void setProductSKUId(Long productSKUId) {
		this.productSKUId = productSKUId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public boolean isReview() {
		return isReview;
	}
	public void setReview(boolean isReview) {
		this.isReview = isReview;
	}

}
