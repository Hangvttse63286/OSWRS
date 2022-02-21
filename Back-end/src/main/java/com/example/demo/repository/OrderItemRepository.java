package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	List<OrderItem> findByOrder(Order order);

	@Modifying
	@Query(value = "select * from order_items b where b.order_id=:order_id and b.product_sku_id=:product_sku_id", nativeQuery=true)
	Optional<OrderItem> findByOrderAndProductSKU(@Param("order_id") Long orderId, @Param("product_sku_id") Long productSKUId);

	@Modifying
	@Query(value = "delete from order_items b where b.order_id=:order_id and b.product_sku_id=:product_sku_id", nativeQuery=true)
	void deleteByOrderAndProductSKU(@Param("order_id") Long orderId, @Param("product_sku_id") Long productSKUId);
}
