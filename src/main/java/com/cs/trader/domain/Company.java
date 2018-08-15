package com.cs.trader.domain;

public class Company {
	int companyID;
	String companyName;
	String ticker;
	Sector sector; 
	
	public Company() {
		super();
	}

	public Company(int companyID, String companyName, String ticker, Sector sector) {
		super();
		this.companyID = companyID;
		this.companyName = companyName;
		this.ticker = ticker;
		this.sector = sector;
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String name) {
		this.companyName = name;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}
}