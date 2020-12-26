package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public class UserLoginData {
	private int id;
	@Nullable private String email;
	@Nullable private String phoneNumber;
	@Nullable private String password;
	
	public UserLoginData(int id, String email, String phoneNumber, String password) {
		super();
		this.id = id;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
}