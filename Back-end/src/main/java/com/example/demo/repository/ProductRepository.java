package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
//	Optional<Product> findByProduct_name(String name);
//	Optional<Product> findBySearch_word(String word);
//	Boolean existsByProduct_id(String id);
//	Boolean existsByProduct_name(String name);
	@EntityGraph(attributePaths = { "categories", "productSKUs", "product_Image" })
	List<Product> findAll();
	//void deleteByProduct_id(String id);

	//@Query(value = "select * FROM products where categories.id = ?1" , nativeQuery = true)
	@EntityGraph(attributePaths = { "categories", "productSKUs", "product_Image" })
	List<Product> findByCategoriesId(Long id);

	@EntityGraph(attributePaths = { "categories", "productSKUs", "product_Image" })
	List<Product> findByProductSKUsId(Long id);

	@EntityGraph(attributePaths = { "categories", "productSKUs", "product_Image" })
	List<Product> findAllByOrderBySoldDesc();

	@EntityGraph(attributePaths = { "categories", "productSKUs", "product_Image" })
	@Query("SELECT p FROM Product p WHERE CONCAT(p.product_name, ' ', p.description_details) LIKE %?1%")
    public List<Product> search(String keyword);

}
