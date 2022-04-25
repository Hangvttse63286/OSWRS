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

import com.example.demo.entity.Product_SKU;
import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductSkuDTO;
import com.example.demo.service.ProductSKUService;
import com.example.demo.service.ProductService;
@RestController
@RequestMapping("/api/admin/productSKU")
public class ProductSKUController {
	private final ProductSKUService productSKUService;
	private final ProductService productService;
	public ProductSKUController(ProductSKUService productSKUService, ProductService productService) {
		super();
		this.productSKUService= productSKUService;
		this.productService= productService;
	}
//	@RequestMapping(value = "/listProduct_SKU", method = RequestMethod.GET)
//	public ResponseEntity<?> listProductSKU() {
//		List<ProductSkuDTO> productSKUList = productSKUService.listAllProductSku();
//		if(!productSKUList.isEmpty())
//			return new ResponseEntity<>(productSKUList, HttpStatus.OK);
//		else
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}

	@RequestMapping(value = "/getProductBySKUId/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProducSKUById(@PathVariable(name = "id") Long id) {
		try {
			ProductSkuDTO productSkuDTO= productSKUService.getSkuById(id);
			return new ResponseEntity<>(productSkuDTO, HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/updateProductBySKUId/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProductSKUById(@PathVariable(name = "id") Long id, @RequestBody ProductSkuDTO productSkuDTO) {
		try {
			ProductSkuDTO product= productSKUService.updateProductSkuById(id, productSkuDTO);
			return new ResponseEntity<>(product, HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/deleteProductBySKUById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProductSKUById(@PathVariable(name = "id") Long id) {
		try {
			productSKUService.deleteProductSkuById(id);
			return new ResponseEntity<>("Delete product sku successfully!", HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/createSKU/{product_id}", method = RequestMethod.POST)
	public ResponseEntity<?> createSKU(@PathVariable(name = "product_id") String product_id, @RequestBody ProductSkuDTO productSkuDTO) {
		try {
			ProductSkuDTO product_SKU = productSKUService.createProductSku(product_id, productSkuDTO);
			return new ResponseEntity<>(product_SKU, HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
