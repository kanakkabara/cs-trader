package com.cs.trader.services;

import com.cs.trader.dao.OrderDao;
import com.cs.trader.dao.TraderDao;
import com.cs.trader.domain.ActivitySummary;
import com.cs.trader.domain.Order;
import com.cs.trader.domain.Trader;
import com.cs.trader.domain.TraderRank;
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
	
	public int addTrader(Trader trader) {
		int status = traderDao.addTrader(trader);
		return status;
		
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
		return orderDao.findActivitySummaryByTraderId(id);
	}

	public List<Order> findOrdersByTraderId(long id) {
		List<Order> orders = orderDao.findOrderByTraderId(id);
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
