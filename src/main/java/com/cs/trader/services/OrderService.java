package com.cs.trader.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.trader.dao.OrderDao;
import com.cs.trader.domain.Order;

@Component
public class OrderService {
	@Autowired
	OrderDao orderDao;

	public long placeNewOrder(String symbol, String instruction, String type, Double price, int volume, long traderId) {
		
		return orderDao.addOrder(symbol, instruction, type, price, volume, traderId);
	};
	
	public List<Order> retrieveOrdersByTrader(long traderId) {
		return orderDao.findOrderByTraderId(traderId);
	}
	
}



