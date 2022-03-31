package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
	@EntityGraph(attributePaths = { "roles", "passwordResetToken", "verification" })
	Optional<User> findByEmail(String email);

	@EntityGraph(attributePaths = { "roles", "passwordResetToken", "verification" })
    Optional<User> findByUsernameOrEmail(String username, String email);

	@EntityGraph(attributePaths = { "roles", "passwordResetToken", "verification" })
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @EntityGraph(attributePaths = { "roles", "passwordResetToken", "verification" })
    List<User> findAll();

    void deleteById(Long id);
}
