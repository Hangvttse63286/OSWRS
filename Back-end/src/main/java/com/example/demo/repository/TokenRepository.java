package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{
	Optional<Token> findByToken(String token);
}
