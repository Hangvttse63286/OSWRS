package com.example.demo.payload;

public class CartTotalResponse {
	private String code;
	private Double discountValue;
	private Double newTotal;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
	}
	public Double getNewTotal() {
		return newTotal;
	}
	public void setNewTotal(Double newTotal) {
		this.newTotal = newTotal;
	}


}
