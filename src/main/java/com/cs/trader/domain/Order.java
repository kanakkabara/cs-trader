package com.cs.trader.domain;

import java.util.Date;

public class Order {
	private long orderId;
	private String symbol;
	private String instruction;
	private String orderType;
	private Double price;
	private int volume;
	private Date placementTimestamp;
	private long traderId;
	private String status;
	private boolean deleted;
	
	public Order(String symbol, String instruction, String orderType, Double price, int volume, long traderId) {
		super();
		this.symbol = symbol;
		this.instruction = instruction;
		this.orderType = orderType;
		this.price = price;
		this.volume = volume;
		this.traderId = traderId;
	}
	
	public Order() {}

	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public Date getPlacementTimestamp() {
		return placementTimestamp;
	}
	public void setPlacementTimestamp(Date placementTimestamp) {
		this.placementTimestamp = placementTimestamp;
	}
	public long getTraderId() {
		return traderId;
	}
	public void setTraderId(long traderId) {
		this.traderId = traderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
