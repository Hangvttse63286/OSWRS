package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Address;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.entity.Voucher;

public interface OrderRepository extends JpaRepository<Order, Long>{
	@EntityGraph(attributePaths = { "user", "address", "payment", "voucher", "orderItems" })
	List<Order> findByUser(User user);
	@EntityGraph(attributePaths = { "user", "address", "payment", "voucher", "orderItems" })
	List<Order> findByVoucher(Voucher voucher);
	@EntityGraph(attributePaths = { "user", "address", "payment", "voucher", "orderItems" })
	List<Order> findByAddress(Address address);
	@EntityGraph(attributePaths = { "user", "address", "payment", "voucher", "orderItems" })
	List<Order> findByUserOrderByOrderDateDesc(User user);
	@EntityGraph(attributePaths = { "user", "address", "payment", "voucher", "orderItems" })
	List<Order> findAllByOrderByOrderDateDesc();
	@EntityGraph(attributePaths = { "user", "address", "payment", "voucher", "orderItems" })
	void deleteById(Long id);
	@EntityGraph(attributePaths = { "user", "address", "payment", "voucher", "orderItems" })
	Optional<Order> findByIdAndUser(Long id, User user);

	boolean existsByIdAndUser(Long id, User user);
}
