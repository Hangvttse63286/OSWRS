package com.example.demo.payload;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.example.demo.entity.Role;

public class UserDto {
	private String username;
    private String email;
    private String first_name;
    private String last_name;
    private String phone_number;
    private int gender_id;
    private Date birthday;
    private List<String> roles;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public int getGender_id() {
		return gender_id;
	}
	public void setGender_id(int gender_id) {
		this.gender_id = gender_id;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> set) {
		this.roles = set;
	}
    
    
}
