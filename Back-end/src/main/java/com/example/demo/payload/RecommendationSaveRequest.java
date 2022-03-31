package com.example.demo.payload;

import java.util.ArrayList;
import java.util.List;

public class RecommendationSaveRequest {
	String username;
	List<String> imageUrlList = new ArrayList<>();
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getImageUrlList() {
		return imageUrlList;
	}
	public void setImageUrlList(List<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}


}
