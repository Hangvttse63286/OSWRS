package com.example.demo.payload;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product_SKU;

public class ProductDetailDTO {
	List<String> urlImage;
    private String product_id;
	private String product_status_id;
	private String product_name;
	private String description_details;
	private float price;
	private List<ProductSkuDTO> productSKUs;

	public ProductDetailDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductDetailDTO(List<String> urlImage, String product_id, String description_details, String product_name, String product_status_id, float price, List<ProductSkuDTO> product_SKUs ) {
		this.urlImage= urlImage;
		this.product_id= product_id;
		this.description_details= description_details;
		this.product_name= product_name;
		this.product_status_id= product_status_id;
		this.price= price;
		this.productSKUs= product_SKUs;
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

	public void setUrlImage(List<String> urlImage) {
		this.urlImage = urlImage;
	}
	public List<String> getUrlImage() {
		return urlImage;
	}
	public void setDescription_details(String description_details) {
		this.description_details = description_details;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}public void setProduct_status_id(String product_status_id) {
		this.product_status_id = product_status_id;
	}
	public String getDescription_details() {
		return description_details;
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
}
