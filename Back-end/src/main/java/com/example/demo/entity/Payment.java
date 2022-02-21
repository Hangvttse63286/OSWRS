package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.common.EPayment;

@Entity
@Table(name = "payments")
public class Payment {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Enumerated(EnumType.STRING)
    private EPayment name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EPayment getName() {
		return name;
	}

	public void setName(EPayment name) {
		this.name = name;
	}


}
