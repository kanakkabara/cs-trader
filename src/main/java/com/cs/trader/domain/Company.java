package com.cs.trader.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
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
