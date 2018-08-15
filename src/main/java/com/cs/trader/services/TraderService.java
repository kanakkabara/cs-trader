package com.cs.trader.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trader.dao.OrderDao;
import com.cs.trader.dao.TraderDao;
import com.cs.trader.domain.ActivitySummary;
import com.cs.trader.domain.Order;
import com.cs.trader.domain.Trader;
import com.cs.trader.exceptions.TraderStillWorkingException;

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
	
	public ActivitySummary findActivitySummaryByTraderId(long id) {
		return orderDao.findActivitySummaryByTraderId(id);
	}

	public List<Order> findOrdersByTraderId(long id) {
		List<Order> orders = orderDao.findOrderByTraderId(id);
		return orders;
	}
	
}
