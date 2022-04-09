package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Long>{
	Optional<Voucher> findByCode(String code);

	boolean existsByCode(String code);

}
