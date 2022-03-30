package com.example.demo.repository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PasswordResetToken;
import com.example.demo.entity.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	@EntityGraph(attributePaths = { "user" })
	PasswordResetToken findByToken(String token);
	@EntityGraph(attributePaths = { "user" })
    PasswordResetToken findByUser(User user);
}
