package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.example.demo.common.EVoucherType;

@Entity
@Table(name = "vouchers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"})
})
public class Voucher {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	private String code;
	private String name;
	private String description;

	@Enumerated(EnumType.STRING)
	private EVoucherType type;

	private Double minSpend;
	private Double maxDiscount;
	private Double discountAmount;
	private boolean isActive;

	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public EVoucherType getType() {
		return type;
	}
	public void setType(EVoucherType type) {
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
