package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "product_sku")
public class Product_SKU {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long product_sku_id;
	private long product_id;
	private int stock;
	private int sale_limit;
	private String size;
	private float price;
	private boolean is_deleted;
	public long getProduct_sku_id() {
		return product_sku_id;
	}
	public void setProduct_sku_id(long product_sku_id) {
		this.product_sku_id = product_sku_id;
	}
	public long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getSale_limit() {
		return sale_limit;
	}
	public void setSale_limit(int sale_limit) {
		this.sale_limit = sale_limit;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public boolean isIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}


}
