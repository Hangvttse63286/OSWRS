package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.Products;
import com.example.demo.payload.ProductDTO;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	List<Category> findAll();
	Category findByName(String name);
////	@Query("select new com.example.demo.entity.Product (p.product_id, p.description_details, p.description_list, p.discount_id, p.product_name, p.product_status_id, p.search_word) "
////			+ "FROM products p LEFT JOIN product_categories pc on p.product_id = pc.product_id "
////			+ "where p.category_id =: id")
//	

	
}
