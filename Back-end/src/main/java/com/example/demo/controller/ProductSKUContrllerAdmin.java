package com.example.demo.controller;

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
public class ProductSKUContrllerAdmin {
	private final ProductSKUService productSKUService;
	private final ProductService productService;
	public ProductSKUContrllerAdmin(ProductSKUService productSKUService, ProductService productService) {
		super();
		this.productSKUService= productSKUService;
		this.productService= productService;
	}

	@RequestMapping(value = "/listProduct_SKU", method = RequestMethod.GET)
	public ResponseEntity<?> listProductSKU() {
		if(productSKUService.listAllProductSku() != null)
			return new ResponseEntity<>(productSKUService.listAllProductSku(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/getProductBySKUId/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProductBySKUId(@PathVariable(name = "id") Long id) {
		if(productSKUService.getSkuById(id) != null)
			return new ResponseEntity<>(productSKUService.getSkuById(id), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	@RequestMapping(value = "/updateProductBySKUId/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ProductSkuDTO> UpdateProductBySKUById(@PathVariable(name = "id") Long id, @RequestBody ProductSkuDTO productSkuDTO) {
		if(productSKUService.getSkuById(id) != null) {
			return new ResponseEntity<>(productSKUService.updateProductSkuById(id, productSkuDTO), HttpStatus.OK);
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
	@RequestMapping(value = "/createSKU/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> createSKU(@PathVariable(name = "id") String id, @RequestBody ProductSkuDTO PproductSkuDTO) {
		if(productService.getProductById(id) != null) {
			return new ResponseEntity<>(productSKUService.createProductSku(id, PproductSkuDTO), HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
