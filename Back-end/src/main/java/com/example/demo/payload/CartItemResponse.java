package com.example.demo.payload;

public class CartItemResponse {
	private Long id;
	private Long cartId;
	private Long productSKUId;
	private String productSKUName;
	private int quantity;
	private Double price;
	private boolean isStock;
	private String imageUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public Long getProductSKUId() {
		return productSKUId;
	}
	public void setProductSKUId(Long productSKUId) {
		this.productSKUId = productSKUId;
	}
	public String getProductSKUName() {
		return productSKUName;
	}
	public void setProductSKUName(String productSKUName) {
		this.productSKUName = productSKUName;
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
	public boolean isStock() {
		return isStock;
	}
	public void setStock(boolean isStock) {
		this.isStock = isStock;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


}
