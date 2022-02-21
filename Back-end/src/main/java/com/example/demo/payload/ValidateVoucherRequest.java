package com.example.demo.payload;

public class ValidateVoucherRequest {
	private String voucherCode;
	private Double cartTotal;
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public Double getCartTotal() {
		return cartTotal;
	}
	public void setCartTotal(Double cartTotal) {
		this.cartTotal = cartTotal;
	}
	
	
}
