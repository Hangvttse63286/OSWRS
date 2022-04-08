package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Product;
import com.example.demo.entity.Recommendation;
import com.example.demo.entity.User;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long>{
	@EntityGraph(attributePaths = { "user", "product" })
	List<Recommendation> findByUser(User user);

	@EntityGraph(attributePaths = { "user", "product" })
	List<Recommendation> findByProduct(Product product);
}
