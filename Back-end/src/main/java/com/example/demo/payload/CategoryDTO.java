package com.example.demo.payload;

import java.util.Collection;

import com.example.demo.common.ECategory;
import com.example.demo.entity.Products;

public class CategoryDTO {
	private long id;
    private String category_name ;
    private boolean is_deleted;
    //private Collection<Products> products;
    
    public CategoryDTO(long id, String category_name, boolean is_deleted) {
    	this.category_name= category_name;
    	this.id= id;
    	this.is_deleted= is_deleted;
    }
    
    public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
    public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
    
    
    public String getCategory_name() {
		return category_name;
	}
    
    public boolean isIs_deleted() {
		return is_deleted;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public CategoryDTO() {
		// TODO Auto-generated constructor stub
	}
}
