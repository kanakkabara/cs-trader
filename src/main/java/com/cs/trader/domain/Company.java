package com.cs.trader.domain;

public class Company {
	private int companyID;
	private String companyName;
	private String ticker;
	private int sectorID;

	public Company() {
		super();
	}

	public Company(int companyID, String companyName, String ticker, int sectorID) {
		super();
		this.companyID = companyID;
		this.companyName = companyName;
		this.ticker = ticker;
		this.sectorID = sectorID;
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

	public int getSectorID() {
		return sectorID;
	}

	public void setSectorID(int sectorID) {
		this.sectorID = sectorID;
	}
}