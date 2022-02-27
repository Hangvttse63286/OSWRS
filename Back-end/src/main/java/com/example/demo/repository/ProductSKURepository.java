package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.Products;

public interface ProductSKURepository extends JpaRepository<Product_SKU, Long>{
	List<Product_SKU> findAll();
}
