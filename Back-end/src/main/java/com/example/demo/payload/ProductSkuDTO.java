package com.example.demo.payload;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.demo.entity.Products;

public class ProductSkuDTO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	private int stock;
	private int sale_limit;
	private String size;
	private float price;
	private boolean is_deleted;
	private Products products;
	
	public ProductSkuDTO() {	
	}
	
	public ProductSkuDTO(long id,  int stock, int sale_limit, String size, float price, boolean is_deleted, Products products) {	
		this.id= id;
		this.stock= stock;
		this.sale_limit= sale_limit;
		this.size= size;
		this.price= price;
		this.is_deleted= is_deleted;
		this.products= products;
	}
	
public void setProducts(Products products) {
	this.products = products;
}
public Products getProducts() {
	return products;
}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public int getSale_limit() {
		return sale_limit;
	}
	public String getSize() {
		return size;
	}
	public int getStock() {
		return stock;
	}
	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	public void setSale_limit(int sale_limit) {
		this.sale_limit = sale_limit;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
}
