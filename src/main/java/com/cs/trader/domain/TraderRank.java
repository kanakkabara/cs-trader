package com.cs.trader.domain;

public class TraderRank {
	private Trader trader;
	private long units;
	
	public TraderRank() {
		super();
	}
	
	public TraderRank(Trader trader, long units) {
		this.trader = trader;
		this.units = units;
	}

	public Trader getTrader() {
		return trader;
	}

	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	public long getUnits() {
		return units;
	}

	public void setUnits(long units) {
		this.units = units;
	}

	@Override
	public String toString() {
		return "TraderRank [trader=" + trader + ", units=" + units + "]";
	}
	
}
