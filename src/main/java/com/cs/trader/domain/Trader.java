package com.cs.trader.domain;

public class Trader {
	private String firstName, lastName, email, phone, address;
	private long traderId;
	
	public Trader() {
		super();
	}
	
	public Trader(String firstName, String lastName, String email, String phone, String address, long traderId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.traderId = traderId;
	}
	
	public Trader(String firstName, String lastName, String email, String phone, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
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
