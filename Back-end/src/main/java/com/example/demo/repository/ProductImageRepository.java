package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product_Image;
import com.example.demo.entity.Product;

@Repository
public interface ProductImageRepository extends JpaRepository<Product_Image, Long>{
	@EntityGraph(attributePaths = { "products" })
	List<Product_Image> findByProducts(Product product);
	@EntityGraph(attributePaths = { "products" })
	Optional<Product_Image> findByUrl(String url);
	@EntityGraph(attributePaths = { "products" })
	List<Product_Image> findByPrimaries(boolean primaries);
	@EntityGraph(attributePaths = { "products" })
	List<Product_Image> findByProductsAndPrimaries(Product product, boolean primaries);
}
