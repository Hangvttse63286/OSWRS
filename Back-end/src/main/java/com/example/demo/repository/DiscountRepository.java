package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long>{
	Optional<Discount> findById(Long id);
}
