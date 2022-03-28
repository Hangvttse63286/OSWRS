package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductSkuDTO;
import com.example.demo.service.ProductSKUService;
import com.example.demo.service.ProductService;
@RestController
@RequestMapping("/api/admin/productSKU")
public class ProductSKUControllerAdmin {
	private final ProductSKUService productSKUService;
	private final ProductService productService;
	public ProductSKUControllerAdmin(ProductSKUService productSKUService, ProductService productService) {
		super();
		this.productSKUService= productSKUService;
		this.productService= productService;
	}

	@RequestMapping(value = "/listProduct_SKU", method = RequestMethod.GET)
	public ResponseEntity<?> listProductSKU() {
		List<ProductSkuDTO> productSKUList = productSKUService.listAllProductSku();
		if(!productSKUList.isEmpty())
			return new ResponseEntity<>(productSKUList, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/getProductBySKUId/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProductBySKUId(@PathVariable(name = "id") Long id) {
		ProductSkuDTO productSKU = productSKUService.getSkuById(id);
		if(productSKU != null)
			return new ResponseEntity<>(productSKU, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	@RequestMapping(value = "/updateProductBySKUId/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ProductSkuDTO> UpdateProductBySKUById(@PathVariable(name = "id") Long id, @RequestBody ProductSkuDTO productSkuDTO) {
		ProductSkuDTO productSKU = productSKUService.updateProductSkuById(id, productSkuDTO);
		if(productSKU != null) {
			return new ResponseEntity<>(productSKU, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/deleteProductBySKUById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProductBySKUById(@PathVariable(name = "id") Long id) {
		if(productSKUService.getSkuById(id) != null) {
			productSKUService.deleteProductSkuById(id);
			return new ResponseEntity<>("Delete successfully!", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	@RequestMapping(value = "/createSKU/{product_id}", method = RequestMethod.POST)
	public ResponseEntity<?> createSKU(@PathVariable(name = "product_id") String id, @RequestBody ProductSkuDTO productSkuDTO) {
		ProductSkuDTO productSKU = productSKUService.createProductSku(id, productSkuDTO);
		if(productSKU != null) {
			return new ResponseEntity<>(productSKU, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
