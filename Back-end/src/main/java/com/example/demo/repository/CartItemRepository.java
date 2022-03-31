package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product_SKU;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	boolean existsByCartAndProductSKU(Cart cart, Product_SKU productSKU);
	@EntityGraph(attributePaths = {"cart", "productSKU" })
	Optional<CartItem> findByCartAndProductSKU(Cart cart, Product_SKU productSKU);
}
