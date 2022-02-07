package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.common.EGender;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "discounts")
public class Discount {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private EGender name;
    private String description;
    private Double percentage;
    private boolean is_active;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public EGender getName() {
		return name;
	}
	public void setName(EGender name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public boolean isIs_active() {
		return is_active;
	}
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}
    
    
}
