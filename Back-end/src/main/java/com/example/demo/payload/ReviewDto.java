package com.example.demo.payload;

public class ReviewDto {
	private long id;
	private String fullname;
	private int numberRating;
	private String description;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public int getNumberRating() {
		return numberRating;
	}
	public void setNumberRating(int numberRating) {
		this.numberRating = numberRating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
