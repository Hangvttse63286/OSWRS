package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Verification;

public interface VerificationRepository extends JpaRepository<Verification, Long>{
	@EntityGraph(attributePaths = { "user" })
	Optional<Verification> findByVerificationCode(String code);
}
