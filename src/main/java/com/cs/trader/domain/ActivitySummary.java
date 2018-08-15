package com.cs.trader.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivitySummary {
	private Date lastOrderPlacement;
	Map<String, Long> orders;
	
	public ActivitySummary() {
		this.lastOrderPlacement = null;
		this.orders = new HashMap<String, Long>();
	}
	
	public ActivitySummary(Date lastOrderPlacement) {
		this.lastOrderPlacement = lastOrderPlacement;
		this.orders = new HashMap<String, Long>();
	}

	public Date getLastOrderPlacement() {
		return lastOrderPlacement;
	}

	public void setLastOrderPlacement(Date lastOrderPlacement) {
		this.lastOrderPlacement = lastOrderPlacement;
	}

	public Map<String, Long> getOrders() {
		return orders;
	}

	public void setOrders(Map<String, Long> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "ActivitySummary [lastOrderPlacement=" + lastOrderPlacement + ", orders=" + orders + "]";
	}
	
	
}
