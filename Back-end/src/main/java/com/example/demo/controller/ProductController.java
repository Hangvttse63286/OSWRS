package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.Helper;
import com.example.demo.entity.Product;
import com.example.demo.payload.ImgurResponse;
import com.example.demo.payload.ProductCreateDTO;
import com.example.demo.payload.ProductDTO;
import com.example.demo.payload.ProductIncludeImageDTO;
import com.example.demo.payload.ProductDetailDTO;
import com.example.demo.payload.ProductImageDTO;
import com.example.demo.payload.ProductListDTO;
import com.example.demo.service.ProductService;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product/")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

//	//ok
//	@RequestMapping(value = "/listProduct", method = RequestMethod.GET)
//	public ResponseEntity<?> listAllProducts(){
//		List<ProductDTO> productList = productService.listAllProducts();
//		if(!productList.isEmpty())
//			return new ResponseEntity<>(productList, HttpStatus.OK);
//		else
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}

	// ok
	@RequestMapping(value = "/listAllProductIncludeImage", method = RequestMethod.GET)
	public ResponseEntity<?> listAllProducts() {
		List<ProductIncludeImageDTO> productList = productService.listAllProductIncludeImage();
		if (!productList.isEmpty())
			return new ResponseEntity<>(productList, HttpStatus.OK);
		else
			return new ResponseEntity<>("Error: No product found.", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<?> searchProducts(@Param("keyword") String keyword) {
		List<ProductIncludeImageDTO> productList = productService.search(keyword);
		if (!productList.isEmpty())
			return new ResponseEntity<>(productList, HttpStatus.OK);
		else
			return new ResponseEntity<>("Error: No product found!", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/web/listAllProductIncludeImage/{fromIndex}", method = RequestMethod.GET)
	public ResponseEntity<?> listAllProductWeb(@PathVariable int fromIndex) {
		List<ProductIncludeImageDTO> productList = productService.listAllProductIncludeImage();
		int toIndex = fromIndex + 10;
		if (!productList.isEmpty()) {
			if (toIndex < productList.size()) {
				List<ProductIncludeImageDTO> subProductList = productList.subList(fromIndex, toIndex);
				return new ResponseEntity<>(subProductList, HttpStatus.OK);
			}
			else {
				List<ProductIncludeImageDTO> subProductList = productList.subList(fromIndex, productList.size());
				return new ResponseEntity<>(subProductList, HttpStatus.OK);
			}
		} else
			return new ResponseEntity<>("Error: No product found!", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/m/listAllProductIncludeImage/{fromIndex}", method = RequestMethod.GET)
	public ResponseEntity<?> listAllProductMobile(@PathVariable int fromIndex) {
		List<ProductIncludeImageDTO> productList = productService.listAllProductIncludeImage();
		int toIndex = fromIndex + 5;
		if (!productList.isEmpty()) {
			if (toIndex < productList.size()) {
				List<ProductIncludeImageDTO> subProductList = productList.subList(fromIndex, toIndex);
				return new ResponseEntity<>(subProductList, HttpStatus.OK);
			}
			else {
				List<ProductIncludeImageDTO> subProductList = productList.subList(fromIndex, productList.size());
				return new ResponseEntity<>(subProductList, HttpStatus.OK);
			}
		} else
			return new ResponseEntity<>("Error: No product found!", HttpStatus.NOT_FOUND);
	}

//	// ok
//	@RequestMapping(value = "/getProductById/{id}", method = RequestMethod.GET)
//	public ResponseEntity<ProductDetailDTO> getProductById(@PathVariable(name = "id") String id) {
//		ProductDetailDTO productDetailDTO = productService.getProductByIdUser(id);
//		if (productService.getProductById(id) != null)
//			return new ResponseEntity<ProductDetailDTO>(productDetailDTO, HttpStatus.OK);
//		else
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}

//		//ok
//		@RequestMapping(value = "/getProductAllById/{id}", method = RequestMethod.GET)
//		public ResponseEntity<ProductListDTO> getProductByIdAll(@PathVariable(name = "id") String id) {
//			if(productService.getProductById(id) != null)
//				return new ResponseEntity<>(productService.getProductByIdAdmin(id), HttpStatus.OK);
//			else
//				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}

	//ok
		@RequestMapping(value = "/getProductById/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getProductById(@PathVariable(name = "id") String id) {
			try {
				ProductListDTO productListDTO= productService.getProductByIdAdmin(id);
				return new ResponseEntity<>(productListDTO, HttpStatus.OK);
			} catch (NullPointerException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}

		//ok
		@RequestMapping(value = "/admin/deleteProductById/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<?> deleteProductById(@PathVariable(name = "id") String id) {
			try {
				productService.deleteProduct(id);
				return new ResponseEntity<>("Delete product successfully!", HttpStatus.OK);
			} catch (NullPointerException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}

		@RequestMapping(value = "admin/updateProductById/{id}", method = RequestMethod.PUT)
		public ResponseEntity<?> updateProductById(@PathVariable(name = "id") String id, @RequestBody ProductDTO productDTO) {
			try {
				ProductDTO product= productService.updateProductById(id, productDTO);
				return new ResponseEntity<>(product, HttpStatus.OK);
			} catch (NullPointerException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
		}

//		@RequestMapping(value = "/admin/createProduct", method = RequestMethod.POST)
//		public ResponseEntity<?> createProduct(@RequestBody ProductDTO productRequest) {
//			Product product= productService.createProduct(productRequest);
//			if( product == null) {
//				return new ResponseEntity<>(HttpStatus.CONFLICT);
//			}
//			else {
//				return new ResponseEntity<>(HttpStatus.OK);
//			}
//		}

		@RequestMapping(value = "/admin/createProductAll", method = RequestMethod.POST,
				consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
		public ResponseEntity<?> createProductncludeImage(
				@RequestPart("fileImage") MultipartFile[] multipartFile, ProductCreateDTO productRequest) throws Exception {

			List<ProductImageDTO> listImageDTOs= new ArrayList<ProductImageDTO>();

			Helper helper = new Helper();

//			for(int i= 0; i < multipartFile.length; i++) {
	//
//				ImgurResponse res = helper.getDataImgurResponse(multipartFile[i]);
//				ProductImageDTO productImageDTO= new ProductImageDTO();
//				if(i==0) {
//					System.out.print(i);
//					productImageDTO.setPrimaries(true);
//				}
//				else {
//					System.out.print(i);
//					productImageDTO.setPrimaries(false);
//				}
//				System.out.print(i);
//				productImageDTO.setUrl(res.getData().getLink());
//				productImageDTO.setName(multipartFile[i].getOriginalFilename());
	//
//				listImageDTOs.add(productImageDTO);
//			}

			for(MultipartFile multi: multipartFile) {

				ImgurResponse res = helper.getDataImgurResponse(multi);

				ProductImageDTO productImageDTO= new ProductImageDTO();

				productImageDTO.setUrl(res.getData().getLink());

				productImageDTO.setName(multi.getOriginalFilename());

				listImageDTOs.add(productImageDTO);
			}

//			productRequest.setProductImage(listImageDTOs);

			ProductListDTO savedProductIncludeImageDTO = productService.createProductAll(productRequest, listImageDTOs);

			if (savedProductIncludeImageDTO != null)
				return new ResponseEntity<>(savedProductIncludeImageDTO ,HttpStatus.OK);
			return new ResponseEntity<>("Error: Product id is existed in database." ,HttpStatus.CONFLICT);
		}
}
