package com.cs.trader.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivitySummary {
	private Date lastOrderPlacement;
	Map<String, Integer> orders;
	
	public ActivitySummary() {
		this.lastOrderPlacement = null;
		this.orders = new HashMap<String, Integer>();
	}
	
	public ActivitySummary(Date lastOrderPlacement) {
		this.lastOrderPlacement = lastOrderPlacement;
		this.orders = new HashMap<String, Integer>();
	}

	public Date getLastOrderPlacement() {
		return lastOrderPlacement;
	}

	public void setLastOrderPlacement(Date lastOrderPlacement) {
		this.lastOrderPlacement = lastOrderPlacement;
	}

	public Map<String, Integer> getOrders() {
		return orders;
	}

	public void setOrders(Map<String, Integer> orders) {
		this.orders = orders;
	}
	
}
