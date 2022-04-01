package com.example.demo.payload;

import java.util.List;

public class ProductIncludeImageDTO {
	private String product_id;
	private String product_status_id;
	private String product_name;
	private String search_word;
	private float price;
	private String imageUrl;
	
	public ProductIncludeImageDTO ( ) {}
	
	public ProductIncludeImageDTO(String product_id, String product_status_id, String product_name, String search_word, float price, String imageUrl) {
		this.product_id = product_id;
		this.product_status_id = product_status_id;
		this.product_name = product_name;
		this.search_word = search_word;
		this.price= price;
		this.imageUrl= imageUrl;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	public float getPrice() {
		return price;
	}
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
	public String getSearch_word() {
		return search_word;
	}
	public void setSearch_word(String search_word) {
		this.search_word = search_word;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
}
