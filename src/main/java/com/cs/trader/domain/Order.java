package com.cs.trader.domain;

import java.util.Date;

public class Order {
	private long orderId;
	private String symbol;
	private OrderSide side;
	private OrderType type;
	private Double price;
	private int volume;
	private Date placementTimestamp;
	private long traderId;
	private OrderStatus status;

	public Order(String symbol, OrderSide side, OrderType type, Double price, int volume) {
		super();
		this.symbol = symbol;
		this.side = side;
		this.type = type;
		this.price = price;
		this.volume = volume;
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
	public OrderSide getSide() {
		return side;
	}
	public void setSide(OrderSide side) {
		this.side = side;
	}
	public OrderType getType() {
		return type;
	}
	public void setType(OrderType type) {
		this.type = type;
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
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
