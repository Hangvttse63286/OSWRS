package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Verification;

public interface VerificationRepository extends JpaRepository<Verification, Long>{
	Optional<Verification> findByVerificationCode(String code);
}
