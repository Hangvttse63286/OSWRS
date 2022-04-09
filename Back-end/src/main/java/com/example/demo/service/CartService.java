package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product_Image;
import com.example.demo.entity.Product_SKU;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductSKURepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.payload.AddressDto;
import com.example.demo.payload.CartItemDto;
import com.example.demo.payload.CartItemResponse;

@Service
public class CartService {
	@Autowired
	CartRepository cartRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@Autowired
	ProductImageRepository productImageRepository;

	@Autowired
	ProductSKURepository productSKURepository;

	@Autowired
    private UserRepository userRepository;

	public CartItemResponse getCartItem(Long id) {
		if (!isExistCartItem(id))
			return null;

		CartItem cartItem = cartItemRepository.getById(id);
		CartItemResponse cartItemResponse = new CartItemResponse();
		cartItemResponse.setId(id);
		cartItemResponse.setCartId(cartItem.getCart().getId());
		cartItemResponse.setProductSKUId(cartItem.getProductSKU().getId());
		cartItemResponse.setProductSKUName(cartItem.getProductSKU().getProducts().getProduct_name() + ", Size: " + cartItem.getProductSKU().getSize());
		cartItemResponse.setQuantity(cartItem.getQuantity());
		cartItemResponse.setPrice(cartItem.getPrice());
		cartItemResponse.setStock(cartItem.getQuantity() >= cartItem.getProductSKU().getStock() ? true : false);
		cartItemResponse.setImageUrl(getPrimaryImageUrl(cartItem.getProductSKU().getProducts()));

		return cartItemResponse;
	}

	public CartItemResponse getCartItemDto(CartItem cartItem) {
		CartItemResponse cartItemResponse = new CartItemResponse();
		cartItemResponse.setId(cartItem.getId());
		cartItemResponse.setCartId(cartItem.getCart().getId());
		cartItemResponse.setProductSKUId(cartItem.getProductSKU().getId());
		cartItemResponse.setProductSKUName(cartItem.getProductSKU().getProducts().getProduct_name() + ", Size: " + cartItem.getProductSKU().getSize());
		cartItemResponse.setQuantity(cartItem.getQuantity());
		cartItemResponse.setPrice(cartItem.getPrice());
		cartItemResponse.setStock(cartItem.getQuantity() >= cartItem.getProductSKU().getStock() ? true : false);
		cartItemResponse.setImageUrl(getPrimaryImageUrl(cartItem.getProductSKU().getProducts()));

		return cartItemResponse;
	}

	public List<CartItemResponse> getCartItemList(String username) {
		User user = userRepository.findByUsername(username).get();
		Cart cart = cartRepository.findByUser(user).get();
		Set<CartItem> cartItems = cart.getCartItems();
		if (cartItems.isEmpty())
			return new ArrayList<CartItemResponse>();
		List<CartItemResponse> cartItemList = new ArrayList<>();

		for (CartItem cartItem : cartItems) {
			CartItemResponse cartItemResponse = new CartItemResponse();
			cartItemResponse.setId(cartItem.getId());
			cartItemResponse.setCartId(cartItem.getCart().getId());
			cartItemResponse.setProductSKUId(cartItem.getProductSKU().getId());
			cartItemResponse.setProductSKUName(cartItem.getProductSKU().getProducts().getProduct_name() + ", Size: " + cartItem.getProductSKU().getSize());
			cartItemResponse.setQuantity(cartItem.getQuantity());
			cartItemResponse.setPrice(cartItem.getPrice());
			cartItemResponse.setStock(cartItem.getQuantity() > cartItem.getProductSKU().getStock() ? true : false);
			cartItemResponse.setImageUrl(getPrimaryImageUrl(cartItem.getProductSKU().getProducts()));
			cartItemList.add(cartItemResponse);
		}
		return cartItemList;
	}

	public CartItemResponse addToCart (CartItemDto cartItemDto, String username) {
		CartItem cartItem;
		User user = userRepository.findByUsername(username).get();
		Cart cart = cartRepository.findByUser(user).get();
		Product_SKU productSKU = productSKURepository.getById(cartItemDto.getProductSKUId());
		if (isExistCartItem(cart, productSKU)) {
			cartItem = cartItemRepository.findByCartAndProductSKU(cart, productSKU).get();
			cartItem.setQuantity(cartItem.getQuantity() + cartItemDto.getQuantity());
			cartItem.setPrice(cartItem.getPrice() + cartItemDto.getPrice());
		} else {
			cartItem = new CartItem();
			cartItem.setCart(cart);
			cartItem.setProductSKU(productSKURepository.getById(cartItemDto.getProductSKUId()));
			cartItem.setQuantity(cartItemDto.getQuantity());
			cartItem.setPrice(cartItemDto.getPrice());
		}
		cartItemRepository.saveAndFlush(cartItem);
		return getCartItemDto(cartItem);
	}

	public boolean checkStockAndSaleLimit (Long productSKUId, int quantity) {
		Product_SKU productSKU = productSKURepository.getById(productSKUId);
		return (quantity <= productSKU.getStock() && quantity <= productSKU.getSale_limit()) ? true:false;
	}


	public CartItemResponse changeQuantity (CartItemDto cartItemDto) {
		CartItem cartItem = cartItemRepository.getById(cartItemDto.getId());
		cartItem.setQuantity(cartItemDto.getQuantity());
		cartItem.setPrice(cartItemDto.getPrice());
		cartItemRepository.saveAndFlush(cartItem);
		return getCartItemDto(cartItem);
	}

	public void deleteCartItem(Long id) {
		cartItemRepository.deleteById(id);
	}

	public boolean isExistCartItem (Cart cart, Product_SKU productSKU) {
		return cartItemRepository.existsByCartAndProductSKU(cart, productSKU);
	}

	public boolean isExistCartItem (Long id) {
		return cartItemRepository.existsById(id);
	}

	private String getPrimaryImageUrl (Product product) {
		Set<Product_Image> productImageList = product.getProduct_Image();
		for(Product_Image image : productImageList)
			if (image.isPrimary())
				return image.getUrl();
		return null;
	}
}
