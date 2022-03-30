package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{
	@EntityGraph(attributePaths = { "user" })
	Optional<Token> findByToken(String token);
}
