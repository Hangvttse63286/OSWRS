package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findByUser(User user);
	List<Order> findByUserOrderByOrderDateDesc(User user);
	List<Order> findAll();
	void deleteById(Long id);
}
