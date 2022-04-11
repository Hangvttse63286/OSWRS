package com.example.demo.payload;

import java.util.stream.Stream;

public class CartItemDto {
	private Long id;
	private Long productSKUId;
	private int quantity;
	private Double price;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

}
