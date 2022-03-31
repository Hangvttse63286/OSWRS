package com.example.demo.payload;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.demo.entity.Product;

public class ProductSkuDTO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	private int stock;
	private int sale_limit;
	private String size;
	private boolean is_deleted;
	private String product_id;
	
	public ProductSkuDTO() {	
	}
	
	public ProductSkuDTO(long id,  int stock, int sale_limit, String size, boolean is_deleted, String product_id) {	
		this.id= id;
		this.stock= stock;
		this.sale_limit= sale_limit;
		this.size= size;
		this.is_deleted= is_deleted;
		this.product_id= product_id;
		//this.products= products;
	}
	
//public void setProducts(Product products) {
//	this.products = products;
//}
//public Product getProducts() {
//	return products;
//}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public boolean isIs_deleted() {
		return is_deleted;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
