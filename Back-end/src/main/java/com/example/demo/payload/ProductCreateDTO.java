package com.example.demo.payload;

import java.util.List;

public class ProductCreateDTO {
	private List<String> category;
	private String product_id;
	private String product_status_id;
	private String product_name;
	private String description_details;
	private float price;

	public ProductCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductCreateDTO(List<String> category, String product_id, String description_details, String product_name, String product_status_id, float price) {
		this.category= category;
		this.product_id= product_id;
		this.description_details= description_details;
		this.product_name= product_name;
		this.product_status_id= product_status_id;
		this.price= price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getPrice() {
		return price;
	}
	public void setCategory(List<String> category) {
		this.category = category;
	}
	public List<String> getCategory() {
		return category;
	}

	public void setDescription_details(String description_details) {
		this.description_details = description_details;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}public void setProduct_status_id(String product_status_id) {
		this.product_status_id = product_status_id;
	}
	public String getDescription_details() {
		return description_details;
	}
	public String getProduct_id() {
		return product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public String getProduct_status_id() {
		return product_status_id;
	}
}
