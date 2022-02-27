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

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Entity
@Table(name = "products", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id"})})
public class Products {

	@Id
    private String product_id;

	private String product_status_id;
	private String product_name;
	private String description_list;
	private String description_details;
	private String search_word;
	private long discount_id;
<<<<<<< HEAD:Back-end/src/main/java/com/example/demo/entity/Product.java


=======
<<<<<<< Updated upstream:Back-end/src/main/java/com/example/demo/entity/Product.java
	
	
=======
<<<<<<< Updated upstream:Back-end/src/main/java/com/example/demo/entity/Product.java


>>>>>>> Stashed changes:Back-end/src/main/java/com/example/demo/entity/Products.java
>>>>>>> add products-fearture:Back-end/src/main/java/com/example/demo/entity/Products.java
//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "product_categories",
//        joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
//        inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
//	private Set<Category> categories = new HashSet<>();
=======
	

	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "product_categories",
        joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private Collection<Category> categories = new ArrayList();

>>>>>>> Stashed changes:Back-end/src/main/java/com/example/demo/entity/Products.java

	@OneToMany(mappedBy = "products", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Product_SKU> productSKUs;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "discount_id")
//	private Discount discount;
<<<<<<< HEAD:Back-end/src/main/java/com/example/demo/entity/Product.java

//	public void setCategories(Set<Category> categories) {
//		this.categories = categories;
//	}

=======
<<<<<<< Updated upstream:Back-end/src/main/java/com/example/demo/entity/Product.java
	
//	public void setCategories(Set<Category> categories) {
//		this.categories = categories;
//	}
	
=======
<<<<<<< Updated upstream:Back-end/src/main/java/com/example/demo/entity/Product.java

//	public void setCategories(Set<Category> categories) {
//		this.categories = categories;
//	}

=======
	
	
	
//	@OneToMany(mappedBy = "products")
//    private Set<Product_Image> product_Image;
    
	public Products() {
		// TODO Auto-generated constructor stub
	}
	
	public Products(String product_id, String description_details, String description_list,  long discount_id, String product_name, String product_status_id, String search_word, Collection<Category> categories, Set<Product_SKU> product_SKUs ) {
		this.product_id= product_id;
		this.description_details= description_details;
		this.description_list= description_list;
		this.discount_id= discount_id;
		this.product_name= product_name;
		this.product_status_id= product_status_id;
		this.search_word= search_word;
		this.categories= categories;
		this.productSKUs= product_SKUs;
	}
	
	public Collection<Category> getCategories() {
		return categories;
	}
	
	public void setProductSKUs(Set<Product_SKU> productSKUs) {
		this.productSKUs = productSKUs;
	}
	
	public Set<Product_SKU> getProductSKUs() {
		return productSKUs;
	}
	
	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}
	
>>>>>>> Stashed changes:Back-end/src/main/java/com/example/demo/entity/Products.java
>>>>>>> Stashed changes:Back-end/src/main/java/com/example/demo/entity/Products.java
>>>>>>> add products-fearture:Back-end/src/main/java/com/example/demo/entity/Products.java
	public void setDescription_details(String description_details) {
		this.description_details = description_details;
	}
	public void setDescription_list(String description_list) {
		this.description_list = description_list;
	}
	public void setDiscount_id(long discount_id) {
		this.discount_id = discount_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}public void setProduct_status_id(String product_status_id) {
		this.product_status_id = product_status_id;
	}public void setSearch_word(String search_word) {
		this.search_word = search_word;
	}

//	public Set<Category> getCategories() {
//		return categories;
//	}
	public String getDescription_details() {
		return description_details;
	}
	public String getDescription_list() {
		return description_list;
	}
	public long getDiscount_id() {
		return discount_id;
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
	public String getSearch_word() {
		return search_word;
	}


}
