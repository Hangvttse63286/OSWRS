package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.payload.ProductDTO;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	@EntityGraph(attributePaths = { "products" })
	List<Category> findAll();
	@EntityGraph(attributePaths = { "products" })
	Category findByName(String name);

	boolean existsByName(String name);


}
