package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product_Image;
import com.example.demo.entity.Products;

@Repository
public interface ProductImageRepository extends JpaRepository<Product_Image, Long>{
	List<Product_Image> findByProduct(Products product);
	Optional<Product_Image> findByUrl(String url);
}
