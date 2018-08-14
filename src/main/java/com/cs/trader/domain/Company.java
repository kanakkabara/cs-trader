package com.cs.trader.domain;

public class Company {
	public Company(int companyID, String name, String ticker) {
		super();
		this.companyID = companyID;
		this.name = name;
		this.ticker = ticker;
	}
	
	int companyID;
	int sectorID;
	String name;
	String ticker;
}
