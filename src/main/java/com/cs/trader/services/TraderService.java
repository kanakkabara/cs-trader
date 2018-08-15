package com.cs.trader.services;

import java.util.List;

import org.glassfish.jersey.model.internal.RankedComparator.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trader.dao.OrderDao;
import com.cs.trader.dao.TraderDao;
import com.cs.trader.domain.ActivitySummary;
import com.cs.trader.domain.Trader;

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
		//if(!hasExistingOrders){
			status = traderDao.deleteTrader(id);
		//}else{
			//throw new TraderStillWorkingException("Trader has existing orders.");
		//}
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
		
		return null;
	}
	
}
