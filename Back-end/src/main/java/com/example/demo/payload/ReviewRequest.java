package com.example.demo.payload;

public class ReviewRequest {
	private long orderId;
	private long productSKUId;
	private int numberRating;
	private String description;

	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getProductSKUId() {
		return productSKUId;
	}
	public void setProductSKUId(long productSKUId) {
		this.productSKUId = productSKUId;
	}
	public int getNumberRating() {
		return numberRating;
	}
	public void setNumberRating(int numberRating) {
		this.numberRating = numberRating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
