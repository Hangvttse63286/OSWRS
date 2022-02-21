package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.common.EPayment;
import com.example.demo.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
	Optional<Payment> findByName(EPayment name);
}
