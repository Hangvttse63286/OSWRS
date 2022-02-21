package com.example.demo.payload;

public class VoucherDto {
	private Long id;
	private String code;
	private String name;
	private String description;
	private String type;
	private Double minSpend;
	private Double maxDiscount;
	private Double discountAmount;
	private boolean isActive;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getMinSpend() {
		return minSpend;
	}
	public void setMinSpend(Double minSpend) {
		this.minSpend = minSpend;
	}
	public Double getMaxDiscount() {
		return maxDiscount;
	}
	public void setMaxDiscount(Double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


}
