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
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Entity
@Table(name = "product_image", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_image_id"})})
public class Product_Image {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long product_image_id;
	private String name;
	private String url;
	private boolean primaries;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id") 
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Products products;
	
	
	public Product_Image(long product_image_id, String name, String url, boolean primary, Products products) {
		super();
		this.product_image_id = product_image_id;
		this.name = name;
		this.url = url;
		this.primaries = primary;
		this.products = products;
	}
	
	public Product_Image() {
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}
	
	public String getName() {
		return name;
	}
	public long getProduct_image_id() {
		return product_image_id;
	}
	public String getUrl() {
		return url;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setProduct_image_id(long product_image_id) {
		this.product_image_id = product_image_id;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setPrimary(boolean primary) {
		this.primaries = primary;
	}
	
	 public boolean isPrimary() {
		return primaries;
	}
}
