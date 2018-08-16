package com.cs.trader.domain;

import java.util.Date;

public class Quote {
	/*QUOTE_ID BIGINT(20) NOT NULL AUTO_INCREMENT,
	BUY_ID BIGINT(20) NOT NULL,
	SELL_ID BIGINT(20) NOT NULL,
	PRICE DOUBLE(20) DEFAULT NULL,
	VOLUME INT(20) NOT NULL,
	FULFILLED_TIMESTAMP TIMESTAMP NOT NULL,*/
	
	private long quoteId, buyId, sellId;
	private double price;
	private int volume;
	private Date fulfilledTimestamp;
	
	public Quote() {
		super();
	}
	public Quote(long quoteId, long buyId, long sellId, double price, int volume, Date fulfilledTimestamp) {
		super();
		this.quoteId = quoteId;
		this.buyId = buyId;
		this.sellId = sellId;
		this.price = price;
		this.volume = volume;
		this.fulfilledTimestamp = fulfilledTimestamp;
	}
	public long getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(long quoteId) {
		this.quoteId = quoteId;
	}
	public long getBuyId() {
		return buyId;
	}
	public void setBuyId(long buyId) {
		this.buyId = buyId;
	}
	public long getSellId() {
		return sellId;
	}
	public void setSellId(long sellId) {
		this.sellId = sellId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public Date getFulfilledTimestamp() {
		return fulfilledTimestamp;
	}
	public void setFulfilledTimestamp(Date fulfilledTimestamp) {
		this.fulfilledTimestamp = fulfilledTimestamp;
	}
	@Override
	public String toString() {
		return "Quote [quoteId=" + quoteId + ", buyId=" + buyId + ", sellId=" + sellId + ", price=" + price
				+ ", volume=" + volume + ", fulfilledTimestamp=" + fulfilledTimestamp + "]";
	}
	
}
