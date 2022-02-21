package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Address;
import com.example.demo.entity.User;

public interface AddressRepository extends JpaRepository<Address, Long>{
	List<Address> findByUser(User user);
	Optional<Address> findById(Long id);

	@Modifying
	@Query(value = "SELECT TOP 1 * FROM addresses b WHERE b.user_id=:user_id ORDER BY b.id DESC", nativeQuery=true)
	Optional<Address> findLatestByUserId(@Param("user_id")Long userId);

	void deleteById(Long id);
}
