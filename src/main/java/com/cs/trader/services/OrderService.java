package com.cs.trader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.trader.dao.OrderDao;

@Component
public class OrderService {
	@Autowired
	OrderDao orderDao;

	public long placeNewOrder(String symbol, String instruction, String type, Double price, int volume, long traderId) {
		
		return orderDao.addOrder(symbol, instruction, type, price, volume, traderId);
	};
	
}



