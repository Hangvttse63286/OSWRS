package com.example.demo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.common.ECategory;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(DemoApplication.class, args);
//		ProductRepository productRepository= context.getBean(ProductRepository.class);
//		productRepository.save(ob1());
	}

//	private static Product ob1() {
//		Product products= new Product();
//		products.setDescription_details("Jeans for men");
//		products.setDescription_list("Jeans for men");
//		products.setDiscount_id(20);
//		products.setProduct_id("JS001");
//		products.setProduct_status_id("instok");
//		products.setProduct_name("Jeans");
//		products.setSearch_word("JS");
//		
//		Category category= new Category();
//		category.setCategory_name(ECategory.JEANS);
//		category.setIs_deleted(false);
//		List<Category> categories= Collections.singletonList(category);
//		
//		products.setCategories(categories);
//		return products;
//	}
}
