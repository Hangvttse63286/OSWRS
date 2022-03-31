package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.example.demo.common.ECategory;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "categories", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Category {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@Enumerated(EnumType.STRING)
    @Column(length = 60)
    private String name ;
    private boolean is_deleted;

    @ManyToMany(mappedBy = "categories",
    		fetch = FetchType.LAZY,
    		cascade =
        {
                CascadeType.DETACH,
                CascadeType.MERGE,
                CascadeType.REFRESH,
                CascadeType.PERSIST
        },
        targetEntity = Product.class)
    @JsonBackReference
    private Set<Product> products = new HashSet<>();

    public Category() {

    }

    public Category(long id, String category_name, boolean is_deleted) {
    	this.name= category_name;
    	this.id= id;
    	this.is_deleted= is_deleted;
    }

    public void setId(long id) {
		this.id = id;
	}
    public long getId() {
		return id;
	}

    public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
    public boolean isIs_deleted() {
		return is_deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
