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
}
