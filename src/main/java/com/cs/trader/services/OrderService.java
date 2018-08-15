package com.cs.trader.services;

import com.cs.trader.dao.OrderDao;
import com.cs.trader.domain.Company;
import com.cs.trader.domain.Order;
import com.cs.trader.domain.OrderStatus;
import com.cs.trader.domain.Trader;
import com.cs.trader.exceptions.CompanyNotFoundException;
import com.cs.trader.exceptions.InvalidFieldException;
import com.cs.trader.exceptions.TraderNotFoundException;
import com.cs.trader.exceptions.UnauthorizedOperationsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.BadRequestException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class OrderService {
	final HashSet<String> instructionsSet = new HashSet<>(Arrays.asList("BUY", "SELL"));
	final HashSet<String> orderTypesSet = new HashSet<>(Arrays.asList("LIMIT", "MARKET"));

	@Autowired
	OrderDao orderDao;

	@Autowired
	TraderService traderService;

	@Autowired
	CompanyService companyService;

	public long placeNewOrder(Order order) {
		if(!instructionsSet.contains(order.getInstruction())) {
			throw new InvalidFieldException("Invalid value for 'instruction' field");
		}

		if(!orderTypesSet.contains(order.getOrderType())) {
			throw new InvalidFieldException("Invalid value for 'type' field");
		}

		if((order.getOrderType().equals("MARKET") && order.getPrice() != null ) ||
				(order.getOrderType().equals("LIMIT") && (order.getPrice() == null || order.getPrice() <= 0))) {
			throw new InvalidFieldException("Invalid value for 'price' field");
		}

		if(order.getVolume() <= 0) {
			throw new InvalidFieldException("Invalid value for 'volume' field");
		}

		try {
			Company company = companyService.validateCompanyByTicker(order.getSymbol());
			Trader trader = traderService.findTraderById(order.getTraderId());
		} catch (TraderNotFoundException ex) {
			throw new InvalidFieldException("Invalid value for 'traderId' field");
		} catch (CompanyNotFoundException ex) {
			throw new InvalidFieldException("Invalid value for 'symbol' field");
		}


		// TODO: *fel* update transaction log
		return orderDao.addOrder(order);
	}

	public Order retrieveOrderByOrderId(long orderId) {
		return orderDao.findOrderByOrderId(orderId);
	}

	public List<Order> retrieveOrdersByTrader(long traderId) {
		return orderDao.findOrderByTraderId(traderId);
	}

	public List<Order> retrieveOrdersByCompany(Company company) {
		String tickerSymbol = company.getTicker();
		return orderDao.findOrdersBySymbol(tickerSymbol);
	}


	public int cancelOrder(long orderId, long traderId) {
		Order order = orderDao.findOrderByOrderId(orderId);

		if(order.getTraderId() != traderId) {
			throw new UnauthorizedOperationsException("Trader does not have write access on the requested order");
		}

		if(!order.getStatus().equals(OrderStatus.OPEN.toString())){
			throw new BadRequestException("Order is already fulfilled or cancelled");
		}

		return orderDao.setOrderStatus(orderId, OrderStatus.CANCELLED.toString());
	}
	
}



