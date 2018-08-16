package com.cs.trader.services;

import com.cs.trader.dao.OrderDao;
import com.cs.trader.dao.TraderDao;
import com.cs.trader.domain.ActivitySummary;
import com.cs.trader.domain.Order;
import com.cs.trader.domain.Trader;
import com.cs.trader.domain.TraderRank;
import com.cs.trader.exceptions.TraderNotFoundException;
import com.cs.trader.exceptions.TraderStillWorkingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraderService {
	
	@Autowired
	TraderDao traderDao;
	
	@Autowired
	OrderDao orderDao;
	
	public long addTrader(Trader trader) {
		long traderID = traderDao.addTrader(trader);
		return traderID;
		
	}
	
	public int deleteTrader(long id) {
		//validate if trader has existing orders
		int status = -1;
		List<Order> orders = orderDao.findOrderByTraderId(id);
		if(orders.size() > 0) {
			throw new TraderStillWorkingException("Trader has existing orders.");
		}
		status = traderDao.deleteTrader(id);
		return status;
	}
	
	public List<Trader> findTraders(){
		return traderDao.findTraders();
	}
	
	public Trader findTraderById(long id) {
		return traderDao.findTraderById(id);
	}

	public Trader findTraderIdByUsername(String username) {
		return traderDao.findTraderByUsername(username);
	}

	public ActivitySummary findActivitySummaryByTraderId(long id) {
		ActivitySummary summary = orderDao.findActivitySummaryByTraderId(id);
		if(summary.getLastOrderPlacement() == null) {
			throw new TraderNotFoundException("Either trader ID is invalid, or trader has no activities");
		}
		return summary;

	}

	public List<Order> findOrdersByTraderId(long id) {
		List<Order> orders = orderDao.findOrderByTraderId(id);
		if(orders == null || orders.size() == 0) {
			throw new TraderNotFoundException("Invalid trader ID");
		}
		return orders;
	}
	
	public List<TraderRank> findTopFiveTradersByNumTrades(){
		List<TraderRank> traders = traderDao.findTopFiveTradersByNumTrades();
		return traders;
	}
	
	public List<TraderRank> findTopFiveTradersByVolume(){
		List<TraderRank> traders = traderDao.findTopFiveTradersByNumTrades();
		return traders;
	}
	
}
