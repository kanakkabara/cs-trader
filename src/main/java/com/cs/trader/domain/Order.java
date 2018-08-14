package com.cs.trader.domain;

import java.sql.Timestamp;

public class Order {
	private double orderId;
	private String symbol;
	private String instruction;
	private String orderType;
	private int price;
	private int volume;
	private Timestamp placementTimestamp;
	private double traderId;
	private String status;
	private boolean fulfilled;
	private boolean deleted;
	
	public double getOrderId() {
		return orderId;
	}
	public void setOrderId(double orderId) {
		this.orderId = orderId;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public Timestamp getPlacementTimestamp() {
		return placementTimestamp;
	}
	public void setPlacementTimestamp(Timestamp placementTimestamp) {
		this.placementTimestamp = placementTimestamp;
	}
	public double getTraderId() {
		return traderId;
	}
	public void setTraderId(double traderId) {
		this.traderId = traderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isFulfilled() {
		return fulfilled;
	}
	public void setFulfilled(boolean fulfilled) {
		this.fulfilled = fulfilled;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
