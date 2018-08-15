package com.cs.trader.services;

import com.cs.trader.dao.OrderDao;
import com.cs.trader.domain.Company;
import com.cs.trader.domain.Order;
import com.cs.trader.exceptions.InvalidFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class OrderService {
	final HashSet<String> instructionsSet = new HashSet<>(Arrays.asList("BUY", "SELL"));
	final HashSet<String> orderTypesSet = new HashSet<>(Arrays.asList("LIMIT", "MARKET"));

	@Autowired
	OrderDao orderDao;
	TraderService traderService;

	public long placeNewOrder(String symbol, String instruction, String type, Double price, int volume, long traderId) {
		String normalizedInstr = instruction.toUpperCase();
		if(!instructionsSet.contains(normalizedInstr)) {
			throw new InvalidFieldException("Invalid value for 'instruction' field");
		}

		String normalizedType = type.toUpperCase();
		if(!orderTypesSet.contains(normalizedType)) {
			throw new InvalidFieldException("Invalid value for 'type' field");
		}

		if(price != null && price <= 0) {
			throw new InvalidFieldException("Invalid value for 'price' field");
		}

		if(volume <= 0) {
			throw new InvalidFieldException("Invalid value for 'volume' field");
		}
//		try {
//			Trader trader = traderService.findTraderById(traderId);
//		} catch (TraderNotFoundException ex) {
//			throw new InvalidFieldException("Invalid value for 'traderId' field");
//		}

		return orderDao.addOrder(symbol, normalizedInstr, normalizedType, price, volume, traderId);
	}

	public List<Order> retrieveOrdersByTrader(long traderId) {
		return orderDao.findOrderByTraderId(traderId);
	}

	public List<Order> retrieveOrdersByCompany(Company company) {
		String tickerSymbol = company.getTicker();
		return orderDao.findOrdersBySymbol(tickerSymbol);
	}
	
}



