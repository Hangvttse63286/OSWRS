package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
//	Optional<Product> findByProduct_name(String name);
//	Optional<Product> findBySearch_word(String word);
//	Boolean existsByProduct_id(String id);
//	Boolean existsByProduct_name(String name);
	List<Product> findAll();
	//void deleteByProduct_id(String id);
}
