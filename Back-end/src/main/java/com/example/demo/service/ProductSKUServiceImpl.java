package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.payload.ProductSkuDTO;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSKURepository;

@Service
public class ProductSKUServiceImpl implements ProductSKUService{

	private final ProductSKURepository productSKURepository;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;
	private final CartItemRepository cartItemRepository;

	public ProductSKUServiceImpl(ProductSKURepository productSKURepository, ProductRepository productRepository, OrderItemRepository orderItemRepository, CartItemRepository cartItemRepository) {
		super();
		this.productSKURepository= productSKURepository;
		this.productRepository= productRepository;
		this.orderItemRepository= orderItemRepository;
		this.cartItemRepository= cartItemRepository;
	}

//	@Override
//	public List<ProductSkuDTO> listAllProductSku() {
//		List<Product_SKU> list= productSKURepository.findAll();
//		if(list.isEmpty()) {
//			throw new NullPointerException("Error: No object found.");
//		}
//		List<ProductSkuDTO> productSkuDTOList= new ArrayList<ProductSkuDTO>();
//		for(Product_SKU pSku: list) {
//			ProductSkuDTO productSkuDTO= new ProductSkuDTO();
//			productSkuDTO.setId(pSku.getId());
//			productSkuDTO.setStock(pSku.getStock());
//			productSkuDTO.setSale_limit(pSku.getSale_limit());
//			productSkuDTO.setSize(pSku.getSize());
//			productSkuDTO.setIs_deleted(pSku.isIs_deleted());
//			productSkuDTOList.add(productSkuDTO);
//		}
//		return productSkuDTOList;
//	}

	@Override
	public void deleteProductSkuById(Long id) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No product sku found."));
		List<OrderItem> orderItems = orderItemRepository.findByProductSKU(product_SKU);
		if (!orderItems.isEmpty())
			orderItemRepository.deleteAllInBatch(orderItems);
		List<CartItem> cartItems = cartItemRepository.findByProductSKU(product_SKU);
		if (!cartItems.isEmpty())
			cartItemRepository.deleteAllInBatch(cartItems);
		productSKURepository.delete(product_SKU);
	}


	@Override
	public ProductSkuDTO updateProductSkuById(Long id, ProductSkuDTO productSkuDTO) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No product sku found."));

		product_SKU.setSize(productSkuDTO.getSize());
		product_SKU.setSale_limit(productSkuDTO.getSale_limit());
		product_SKU.setStock(productSkuDTO.getStock());
		productSKURepository.saveAndFlush(product_SKU);
		return getSku(product_SKU);
	}

	@Override
	public ProductSkuDTO getSkuById(Long id) {
		Product_SKU product_SKU= productSKURepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No product sku found."));

		ProductSkuDTO productSkuDTO= new ProductSkuDTO();
		productSkuDTO.setId(product_SKU.getId());
		productSkuDTO.setStock(product_SKU.getStock());
		productSkuDTO.setSale_limit(product_SKU.getSale_limit());
		productSkuDTO.setSize(product_SKU.getSize());
		productSkuDTO.setProduct_id(product_SKU.getProducts().getProduct_id());
		return productSkuDTO;
	}

	@Override
	public ProductSkuDTO getSku(Product_SKU product_SKU) {

		ProductSkuDTO productSkuDTO= new ProductSkuDTO();
		productSkuDTO.setId(product_SKU.getId());
		productSkuDTO.setStock(product_SKU.getStock());
		productSkuDTO.setSale_limit(product_SKU.getSale_limit());
		productSkuDTO.setSize(product_SKU.getSize());
		productSkuDTO.setProduct_id(product_SKU.getProducts().getProduct_id());
		return productSkuDTO;
	}

	@Override
	public List<ProductSkuDTO> getSKUByProductId(String id) {
		Product products = productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No product found."));
		Set<Product_SKU> productSKUs = products.getProductSKUs();
		if (productSKUs.isEmpty())
			return new ArrayList<ProductSkuDTO>();
		List<ProductSkuDTO> pList= new ArrayList<ProductSkuDTO>();
		for(Product_SKU p: products.getProductSKUs()) {
			ProductSkuDTO productSkuDTO= new ProductSkuDTO();
			productSkuDTO.setId(p.getId());
			productSkuDTO.setSale_limit(p.getSale_limit());
			productSkuDTO.setSize(p.getSize());
			productSkuDTO.setStock(p.getStock());
			productSkuDTO.setProduct_id(products.getProduct_id());
			pList.add(productSkuDTO);
		}
		return pList;
	}

	@Override
	public ProductSkuDTO createProductSku(String id, ProductSkuDTO productRequest) {
		Product products = productRepository.findById(id).orElseThrow(() -> new NullPointerException("Error: No product found."));
		Set<Product_SKU> product_SKU_List= new HashSet<Product_SKU>();

		Product_SKU product_SKU= new Product_SKU();
		product_SKU.setId(productRequest.getId());
		product_SKU.setStock(productRequest.getStock());
		product_SKU.setSale_limit(productRequest.getSale_limit());
		product_SKU.setSize(productRequest.getSize());
		product_SKU.setProducts(products);
		productSKURepository.saveAndFlush(product_SKU);
		return getSku(product_SKU);
	}

}
