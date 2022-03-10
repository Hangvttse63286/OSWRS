package com.example.demo.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path productUploadDir= Paths.get("product-images");
		String productUploadPath= productUploadDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/product-images/**").addResourceLocations("file:/" + productUploadPath + "/");
	}
}
