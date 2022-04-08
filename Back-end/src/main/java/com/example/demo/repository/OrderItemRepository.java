package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product_SKU;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	@EntityGraph(attributePaths = { "order", "productSKU" })
	List<OrderItem> findByOrder(Order order);
	@EntityGraph(attributePaths = { "order", "productSKU" })
	List<OrderItem> findByProductSKU(Product_SKU productSKU);
}
