package com.example.demo.payload;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product_SKU;

public class ProductIncludeSkuDTO {
    private String product_id;
	private String product_status_id;
	private String product_name;
	private String description_list;
	private String description_details;
	private String search_word;
	private long discount_id;
	private List<ProductSkuDTO> productSKUs;
	
	public ProductIncludeSkuDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public ProductIncludeSkuDTO(String product_id, String description_details, String description_list,  long discount_id, String product_name, String product_status_id, String search_word, List<ProductSkuDTO> product_SKUs ) {
		this.product_id= product_id;
		this.description_details= description_details;
		this.description_list= description_list;
		this.discount_id= discount_id;
		this.product_name= product_name;
		this.product_status_id= product_status_id;
		this.search_word= search_word;
		this.productSKUs= product_SKUs;
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
	public void setDiscount_id(long discount_id) {
		this.discount_id = discount_id;
	}
	public void setProduct_id(String product_id) {
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
	public long getDiscount_id() {
		return discount_id;
	}
	public String getProduct_id() {
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
