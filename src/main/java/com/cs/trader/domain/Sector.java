package com.cs.trader.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class Sector {
	private int sectorID;
	private String sectorName;
	private String sectorDesc;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int companyCount;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Company> companies;
	
	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public int getCompanyCount() {
		return companyCount;
	}

	public void setCompanyCount(int companyCount) {
		this.companyCount = companyCount;
	}

	public int getSectorID() {
		return sectorID;
	}

	public void setSectorID(int sectorID) {
		this.sectorID = sectorID;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getSectorDesc() {
		return sectorDesc;
	}

	public void setSectorDesc(String sectorDesc) {
		this.sectorDesc = sectorDesc;
	}
	
	public Sector() {
		super();
	}

	public Sector(int sectorID, String sectorName, String sectorDesc) {
		super();
		this.sectorID = sectorID;
		this.sectorName = sectorName;
		this.sectorDesc = sectorDesc;
		this.companies = null;
	}

	public Sector(int sectorID, String sectorName, String sectorDesc, int companyCount) {
		super();
		this.sectorID = sectorID;
		this.sectorName = sectorName;
		this.sectorDesc = sectorDesc;
		this.companyCount = companyCount;
		this.companies = null;
	}

	public Sector(Sector copy, List<Company> companies) {
		super();
		this.sectorID = copy.sectorID;
		this.sectorName = copy.sectorName;
		this.sectorDesc = copy.sectorDesc;
		this.companies = companies;
	}
}