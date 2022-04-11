//package com.example.demo.controller;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.payload.CategoryDTO;
//import com.example.demo.payload.ProductDetailDTO;
//import com.example.demo.payload.ProductSkuDTO;
//import com.example.demo.repository.ProductSKURepository;
//import com.example.demo.service.CategoryService;
//import com.example.demo.service.ProductSKUService;
//import com.example.demo.service.ProductSKUServiceImpl;
//
//@RestController
//@RequestMapping("/api/productSKU")
//public class ProductSKUControllerUser {
//	private final ProductSKUService productSKUService;
//
//	public ProductSKUControllerUser(ProductSKUService productSKUService) {
//		super();
//		this.productSKUService= productSKUService;
//	}
//
////	@RequestMapping(value = "/listProduct_SKU", method = RequestMethod.GET)
////	public ResponseEntity<?> listProductSKU() {
////		List<ProductSkuDTO> productSKUList = productSKUService.listAllProductSku();
////		if(!productSKUList.isEmpty())
////			return new ResponseEntity<>(productSKUList, HttpStatus.OK);
////		else
////			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////	}
//
//	@RequestMapping(value = "/getSkuById/{id}", method = RequestMethod.GET)
//	public ResponseEntity<?> getSkuById(@PathVariable(name = "id") Long id) {
//		ProductSkuDTO productSkuDTO= productSKUService.getSkuById(id);
//		if(productSKUService.getSkuById(id) != null) {
//			return new ResponseEntity<>(productSkuDTO, HttpStatus.OK);
//		}
//		else
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}
//	@RequestMapping(value = "/getSKUByProductId/{id}", method = RequestMethod.GET)
//	public ResponseEntity<?> getSKUByProductId(@PathVariable(name = "id") String id) {
//		List<ProductSkuDTO> pList= productSKUService.getSKUByProductId(id);
//		if(!pList.isEmpty())
//			return new ResponseEntity<>(pList, HttpStatus.OK);
//		else
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//}
