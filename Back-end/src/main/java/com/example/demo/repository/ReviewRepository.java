package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Product;
import com.example.demo.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	List<Review> findByProducts(Product product);
}
