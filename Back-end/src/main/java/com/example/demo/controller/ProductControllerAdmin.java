package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Category;
import com.example.demo.entity.Products;
import com.example.demo.payload.CategoryDTO;
import com.example.demo.payload.ProductDTO;
<<<<<<< HEAD
=======
<<<<<<< Updated upstream
import com.example.demo.repository.ProductRepository;
=======
<<<<<<< Updated upstream
=======
import com.example.demo.payload.ProductSkuDTO;
import com.example.demo.repository.ProductRepository;
>>>>>>> Stashed changes
>>>>>>> Stashed changes
>>>>>>> add products-fearture
import com.example.demo.service.ProductService;



@RestController
@RequestMapping("/api/product/admin/")
public class ProductControllerAdmin {

	@Autowired
	private ModelMapper modelMapper;
	
	private final ProductService productService;
	
	public ProductControllerAdmin(ProductService productService) {
		super();
		this.productService= productService;
	}
	
	@RequestMapping(value = "/listProduct", method = RequestMethod.GET)
	public List<ProductDTO> listAllProducts(){
		
		return productService.listAllProducts().stream().map(product -> modelMapper.map(product , ProductDTO.class)).collect(Collectors.toList());
	}
	
	
	@RequestMapping(value = "/getProductById/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") String id) {
		Products products= productService.getProductById(id);
		ProductDTO productResponseDto= modelMapper.map(products, ProductDTO.class);
		
		return ResponseEntity.ok().body(productResponseDto);
	}
	
	@RequestMapping(value = "/updateProductById/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ProductDTO> updateProductById(@PathVariable(name = "id") String id, @RequestBody ProductDTO productDTO) {
		Products productRequest= modelMapper.map(productDTO, Products.class);
		Products products= productService.updateProductById(id, productRequest);
		
		ProductDTO productResponseDto= modelMapper.map(products, ProductDTO.class);
		return ResponseEntity.ok().body(productResponseDto);
	}
	
	@RequestMapping(value = "/updateCategoryById/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CategoryDTO> updateCategoryById(@PathVariable(name = "id") Long id, @RequestBody CategoryDTO categoryDTO) {
		Category categoryReq= modelMapper.map(categoryDTO, Category.class);
		Category category= productService.updateCategoryById(id, categoryReq);
		
		CategoryDTO categoryResponseDto= modelMapper.map(category, CategoryDTO.class);
		return ResponseEntity.ok().body(categoryResponseDto);
	}
	
	@RequestMapping(value = "/createProduct", method = RequestMethod.POST)
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		Products productRequest= modelMapper.map(productDTO, Products.class);
		Products products= productService.createProduct(productRequest);
		
		ProductDTO productResponseDto= modelMapper.map(products, ProductDTO.class);
		return new ResponseEntity<ProductDTO>(productResponseDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/createCategory", method = RequestMethod.POST)
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
		Category categoryReq= modelMapper.map(categoryDTO, Category.class);
		Category category= productService.createCategory(categoryReq);
		
		CategoryDTO categoryResponseDto= modelMapper.map(category, CategoryDTO.class);
		return new ResponseEntity<CategoryDTO>(categoryResponseDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteProductById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteProductById(@PathVariable(name = "id") String id) {
		productService.deleteProduct(id);

		    return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@RequestMapping(value = "/deleteCatgoryById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteCatgoryById(@PathVariable(name = "id") Long id) {
		productService.deleteCategory(id);
		try {
			productService.deleteCategory(id);
		    return new ResponseEntity<>(HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@RequestMapping(value = "/deleteProductSkuById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteProductSkuById(@PathVariable(name = "id") Long id) {
		productService.deleteProductSku(id);
		try {
			productService.deleteProductSku(id);
		    return new ResponseEntity<>(HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@RequestMapping(value = "/listCategory", method = RequestMethod.GET)
	public List<CategoryDTO> listCategories() {
		return productService.listAllCategories().stream().map(category -> modelMapper.map(category , CategoryDTO.class)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/listProduct_SKU", method = RequestMethod.GET)
	public List<ProductSkuDTO> listProductSKU() {
		return productService.listAllProductSKU().stream().map(product_sku -> modelMapper.map(product_sku , ProductSkuDTO.class)).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/getProductByCategoryId/{id}", method = RequestMethod.GET)
	public List<ProductDTO> getProductByCategory(@PathVariable(name = "id") Long id) {
		return productService.listProductByCategoryId(id).stream().map(products -> modelMapper.map(products , ProductDTO.class)).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/getProductBySKUId/{id}", method = RequestMethod.GET)
	public List<ProductDTO> getProductBySKUId(@PathVariable(name = "id") Long id) {
		return productService.listProductBySKUId(id).stream().map(products -> modelMapper.map(products , ProductDTO.class)).collect(Collectors.toList());
	}
}
