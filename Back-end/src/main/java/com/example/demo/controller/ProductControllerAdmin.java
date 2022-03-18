package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.Helper;
import com.example.demo.entity.ImgurResponse;
import com.example.demo.entity.Products;
import com.example.demo.payload.ProductCreateDTO;
import com.example.demo.payload.ProductDTO;
import com.example.demo.payload.ProductImageDTO;
import com.example.demo.payload.ProductListDTO;
import com.example.demo.service.ProductService;



@RestController
@RequestMapping("/api/product/admin/")
public class ProductControllerAdmin {

	@Autowired
	private ModelMapper modelMapper;

	private final ProductService productService;
	//private final ProductImageService productImageService;

	public ProductControllerAdmin(ProductService productService) {
		super();
		this.productService= productService;
	}

	//ok
	@RequestMapping(value = "/listProduct", method = RequestMethod.GET)
	public ResponseEntity<?> listAllProducts(){
		if(productService.listAllProducts() != null)
			return new ResponseEntity<>(productService.listAllProducts(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	//ok
	@RequestMapping(value = "/listProductIncludeImage", method = RequestMethod.GET)
	public ResponseEntity<?> listAllProductIncludeImage(){
		if(productService.listAllProducts() != null)
			return new ResponseEntity<>(productService.listAllProductIncludeImage(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	//ok
	@RequestMapping(value = "/getProductById/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductListDTO> getProductById(@PathVariable(name = "id") String id) {
		if(productService.getProductById(id) != null)
			return new ResponseEntity<>(productService.getProductByIdAdmin(id), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	//ok
	@RequestMapping(value = "/deleteProductById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProductById(@PathVariable(name = "id") String id) {
		if(productService.getProductById(id) != null) {
			productService.deleteProduct(id);
			return new ResponseEntity<>("Delete order successfully!", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/updateProductById/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ProductDTO> updateProductById(@PathVariable(name = "id") String id, @RequestBody ProductDTO productDTO) {
		if(productService.getProductById(id) != null) {
			return new ResponseEntity<>(productService.updateProductById(id, productDTO), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/createProduct", method = RequestMethod.POST)
	public ResponseEntity<?> createProduct(@RequestBody ProductDTO productRequest) {
		if(productService.createProduct(productRequest) == null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/createProductAll", method = RequestMethod.POST,
			consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ProductListDTO> createProductncludeImage(
			@RequestPart("fileImage") MultipartFile[] multipartFile, ProductCreateDTO productRequest) throws Exception {

		List<ProductImageDTO> listImageDTOs= new ArrayList<ProductImageDTO>();

		Helper helper = new Helper();

		for(MultipartFile multi: multipartFile) {

			ImgurResponse res = helper.getDataImgurResponse(multi);

			ProductImageDTO productImageDTO= new ProductImageDTO();

			productImageDTO.setUrl(res.getData().getLink());

			productImageDTO.setName(multi.getOriginalFilename());

			listImageDTOs.add(productImageDTO);
		}

		productRequest.setProductImage(listImageDTOs);

		Products savedProductIncludeImageDTO = productService.createProductAll(productRequest);

		return new ResponseEntity<ProductListDTO>(HttpStatus.OK);
	}


}
