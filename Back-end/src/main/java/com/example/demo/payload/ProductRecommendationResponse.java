package com.example.demo.payload;

public class ProductRecommendationResponse {
	private String product_id;
	private String product_status_id;
	private String product_name;
	private float price;
	private String imageUrl;
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_status_id() {
		return product_status_id;
	}
	public void setProduct_status_id(String product_status_id) {
		this.product_status_id = product_status_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


}
