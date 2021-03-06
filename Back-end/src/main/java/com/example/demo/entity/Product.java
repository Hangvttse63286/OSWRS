package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "products", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id"})})
public class Product {

	@Id
    private String product_id;

	private String product_status_id;
	private String product_name;
	private String description_details;
	private float price;
	private int sold;

//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "product_categories",
//        joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
//        inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
//	private Set<Category> categories = new HashSet<>();


	@ManyToMany(fetch = FetchType.LAZY, cascade =
        {
                CascadeType.DETACH,
                CascadeType.MERGE,
                CascadeType.REFRESH,
                CascadeType.PERSIST
        },
        targetEntity = Category.class)
	@JsonManagedReference
    @JoinTable(name = "product_categories",
        joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private Set<Category> categories = new HashSet<>();

	@OneToMany(mappedBy = "products", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Product_SKU> productSKUs= new HashSet<Product_SKU>();

	@OneToMany(mappedBy = "products" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Product_Image> product_Image= new HashSet<Product_Image>();


	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(String product_id, String description_details, String product_name, String product_status_id, float price, Set<Category> categories, Set<Product_SKU> product_SKUs, int sold ) {
		this.product_id= product_id;
		this.description_details= description_details;
		this.product_name= product_name;
		this.product_status_id= product_status_id;
		this.price= price;
		this.categories= categories;
		this.productSKUs= product_SKUs;
		this.sold = sold;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	public float getPrice() {
		return price;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setProductSKUs(Set<Product_SKU> productSKUs) {
		this.productSKUs = productSKUs;
	}

	public Set<Product_SKU> getProductSKUs() {
		return productSKUs;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public void setDescription_details(String description_details) {
		this.description_details = description_details;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}public void setProduct_status_id(String product_status_id) {
		this.product_status_id = product_status_id;
	}

//	public Set<Category> getCategories() {
//		return categories;
//	}
	public String getDescription_details() {
		return description_details;
	}
	public String getProduct_id() {
		return product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public String getProduct_status_id() {
		return product_status_id;
	}

	public void setProduct_Image(Set<Product_Image> product_Image) {
		this.product_Image = product_Image;
	}

	public Set<Product_Image> getProduct_Image() {
		return product_Image;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

}
