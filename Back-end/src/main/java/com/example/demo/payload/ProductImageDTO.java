package com.example.demo.payload;

public class ProductImageDTO {
	private long product_image_id;
	private String name;
	private String url;
	private Long productId;
	private boolean primaries;


	public ProductImageDTO() {
	}

	public ProductImageDTO(long product_image_id, String name, String url, Long productId, boolean primaries) {
		super();
		this.product_image_id = product_image_id;
		this.name = name;
		this.url = url;
		this.productId= productId;
		this.primaries= primaries;
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

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setPrimaries(boolean primaries) {
		this.primaries = primaries;
	}
	public boolean isPrimaries() {
		return primaries;
	}
}
