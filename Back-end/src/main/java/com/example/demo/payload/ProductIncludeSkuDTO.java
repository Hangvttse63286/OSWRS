package com.example.demo.payload;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product_SKU;

public class ProductIncludeSkuDTO {
	List<ProductImageDTO> productImage;
    private Long product_id;
	private String product_status_id;
	private String product_name;
	private String description_list;
	private String description_details;
	private String search_word;
	private float price;
	private List<ProductSkuDTO> productSKUs;

	public ProductIncludeSkuDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductIncludeSkuDTO(List<ProductImageDTO> productImage, Long product_id, String description_details, String description_list, String product_name, String product_status_id, String search_word, float price, List<ProductSkuDTO> product_SKUs ) {
		this.productImage= productImage;
		this.product_id= product_id;
		this.description_details= description_details;
		this.description_list= description_list;
		this.product_name= product_name;
		this.product_status_id= product_status_id;
		this.search_word= search_word;
		this.price= price;
		this.productSKUs= product_SKUs;
	}

	public void setProductImage(List<ProductImageDTO> productImage) {
		this.productImage = productImage;
	}
	public List<ProductImageDTO> getProductImage() {
		return productImage;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getPrice() {
		return price;
	}
	public void setProductSKUs(List<ProductSkuDTO> productSKUs) {
		this.productSKUs = productSKUs;
	}

	public List<ProductSkuDTO> getProductSKUs() {
		return productSKUs;
	}

	public void setDescription_details(String description_details) {
		this.description_details = description_details;
	}
	public void setDescription_list(String description_list) {
		this.description_list = description_list;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}public void setProduct_status_id(String product_status_id) {
		this.product_status_id = product_status_id;
	}public void setSearch_word(String search_word) {
		this.search_word = search_word;
	}

	public String getDescription_details() {
		return description_details;
	}
	public String getDescription_list() {
		return description_list;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public String getProduct_status_id() {
		return product_status_id;
	}
	public String getSearch_word() {
		return search_word;
	}
}
