package com.example.demo.payload;

public class VNPayRefundRequest {
	private String vnpOrderId;
	private String transDate;
	private String email;
	private int amount;
	private String trantype;
	public String getVnpOrderId() {
		return vnpOrderId;
	}
	public void setVnpOrderId(String vnpOrderId) {
		this.vnpOrderId = vnpOrderId;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getTrantype() {
		return trantype;
	}
	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}

}
