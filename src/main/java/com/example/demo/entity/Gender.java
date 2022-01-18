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

@Entity
@Table(name = "genders")
public class Gender {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private EGender name;

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
    
    
}
