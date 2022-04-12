package com.example.demo.payload;


public class CategoryDTO {
	private long id;
    private String category_name ;
    //private Collection<Product> products;

    public CategoryDTO(long id, String category_name) {
    	this.category_name= category_name;
    	this.id= id;
    }

    public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

    public String getCategory_name() {
		return category_name;
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
