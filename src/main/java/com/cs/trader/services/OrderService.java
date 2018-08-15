package com.cs.trader.services;

import com.cs.trader.dao.OrderDao;
import com.cs.trader.domain.Company;
import com.cs.trader.domain.Order;
import com.cs.trader.domain.Trader;
import com.cs.trader.exceptions.InvalidFieldException;
import com.cs.trader.exceptions.TraderNotFoundException;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderService {
	public enum Instructions {
		BUY, SELL;
	}

	public enum OrderType {
		LIMIT, MARKET;
	}

	@Autowired
	OrderDao orderDao;
	TraderService traderService;

	public long placeNewOrder(String symbol, String instruction, String type, Double price, int volume, long traderId) {
		String normalizedInstr = instruction.toUpperCase();
		if(!EnumUtils.isValidEnum(Instructions.class, normalizedInstr)) {
			throw new InvalidFieldException("Invalid value for 'instruction' field");
		}

		String normalizedType = type.toUpperCase();
		if(!EnumUtils.isValidEnum(OrderType.class, normalizedType)) {
			throw new InvalidFieldException("Invalid value for 'type' field");
		}

		if(price != null && price <= 0) {
			throw new InvalidFieldException("Invalid value for 'price' field");
		}

		if(volume <= 0) {
			throw new InvalidFieldException("Invalid value for 'volume' field");
		}
		try {
			Trader trader = traderService.findTraderById(traderId);
		} catch (TraderNotFoundException ex) {
			throw new InvalidFieldException("Invalid value for 'traderId' field");
		}

		return orderDao.addOrder(symbol, normalizedInstr, type, price, volume, traderId);
	}

	public List<Order> retrieveOrdersByTrader(long traderId) {
		return orderDao.findOrderByTraderId(traderId);
	}

	public List<Order> retrieveOrdersByCompany(Company company) {
		String tickerSymbol = company.getTicker();
		return orderDao.findOrdersBySymbol(tickerSymbol);
	}
	
}



