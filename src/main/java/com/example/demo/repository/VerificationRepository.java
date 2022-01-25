package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Verification;

public interface VerificationRepository extends JpaRepository<Verification, Long>{

}
