package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Data
@Entity
@Table(name = "product_sku", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Product_SKU {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	private int stock;
	private int sale_limit;
	private String size;
	private boolean is_deleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product products;


	//, referencedColumnName="product_id"
	public Product_SKU() {
	}

	public Product_SKU(long id, int stock, int sale_limit, String size, boolean is_deleted, Product products) {
		this.id= id;
		this.stock= stock;
		this.sale_limit= sale_limit;
		this.size= size;
		this.is_deleted= is_deleted;
		this.products= products;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Product getProducts() {
		return products;
	}

	public void setProducts(Product products) {
		this.products = products;
	}


}
