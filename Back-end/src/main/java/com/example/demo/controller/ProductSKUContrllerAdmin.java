package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.ProductSkuDTO;
import com.example.demo.service.ProductSKUService;
@RestController
@RequestMapping("/api/productSKU/admin/")
public class ProductSKUContrllerAdmin {
	private final ProductSKUService productSKUService;
	
	public ProductSKUContrllerAdmin(ProductSKUService productSKUService) {
		super();
		this.productSKUService= productSKUService;
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
		if(productSKUService.getProductSkuById(id) != null)
			return new ResponseEntity<>(productSKUService.getProductSkuById(id), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	
	@RequestMapping(value = "/updateProductBySKUId/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ProductSkuDTO> UpdateProductBySKUById(@PathVariable(name = "id") Long id, @RequestBody ProductSkuDTO productSkuDTO) {
		if(productSKUService.getProductSkuById(id) != null) {
			return new ResponseEntity<>(productSKUService.updateProductSkuById(id, productSkuDTO), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/deleteProductBySKUById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProductBySKUById(@PathVariable(name = "id") Long id, @RequestBody ProductSkuDTO productSkuDTO) {
		if(productSKUService.getProductSkuById(id) != null) {
			productSKUService.deleteProductSkuById(id);
			return new ResponseEntity<>("Delete successfully!", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
