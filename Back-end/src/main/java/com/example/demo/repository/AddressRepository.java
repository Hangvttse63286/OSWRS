package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Address;
import com.example.demo.entity.User;

public interface AddressRepository extends JpaRepository<Address, Long>{
	@EntityGraph(attributePaths = { "user" })
	List<Address> findByUser(User user);
	@EntityGraph(attributePaths = { "user" })
	Optional<Address> findById(Long id);

	void deleteById(Long id);
}
