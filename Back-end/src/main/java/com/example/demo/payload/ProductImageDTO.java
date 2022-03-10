package com.example.demo.payload;

public class ProductImageDTO {
	private long product_image_id;
	private String name;
	private String url;
	
	
	public ProductImageDTO() {
	}

	public ProductImageDTO(long product_image_id, String name, String url) {
		super();
		this.product_image_id = product_image_id;
		this.name = name;
		this.url = url;
	}

	public long getProduct_image_id() {
		return product_image_id;
	}

	public void setProduct_image_id(long product_image_id) {
		this.product_image_id = product_image_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
