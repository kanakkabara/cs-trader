package com.cs.trader.domain;

public class Trader {
	private String firstName, lastName, email, phone, address, username;
	private long traderId;
	
	public Trader() {
		super();
	}
	
	public Trader(String firstName, String lastName, String email, String phone, String address, long traderId, 
			String username) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.traderId = traderId;
		this.username = username;
	}
	
	public Trader(String firstName, String lastName, String email, String phone, String address, String username) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public long getTraderId() {
		return traderId;
	}
	
	public void setTraderId(long traderId) {
		this.traderId = traderId;
	}
	
}
