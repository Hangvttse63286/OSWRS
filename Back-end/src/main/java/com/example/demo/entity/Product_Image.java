package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
	private String product_id;
	private String name;
	private String url;

//	@ManyToOne
//	private Products products;

	public String getName() {
		return name;
	}
	public String getProduct_id() {
		return product_id;
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
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public void setProduct_image_id(long product_image_id) {
		this.product_image_id = product_image_id;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
