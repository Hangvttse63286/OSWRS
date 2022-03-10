package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product_Image;

@Repository
public interface ProductImageRepository extends JpaRepository<Product_Image, Long>{

}
