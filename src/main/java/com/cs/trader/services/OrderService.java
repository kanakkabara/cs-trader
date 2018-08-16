package com.cs.trader.services;

import com.cs.trader.dao.OrderDao;
import com.cs.trader.domain.*;
import com.cs.trader.exceptions.CompanyNotFoundException;
import com.cs.trader.exceptions.InvalidFieldException;
import com.cs.trader.exceptions.UnauthorizedOperationsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.BadRequestException;
import java.util.*;
import java.util.stream.Collectors;

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

	@Autowired
	TransactionService transactionService;

	public long placeNewOrder(Order order, String username) {
//		if(!instructionsSet.contains(order.getSide())) {
//			throw new InvalidFieldException("Invalid value for 'instruction' field");
//		}

//		validateOrderType(order.getType());
		validatePrice(order.getPrice(), order.getType());
		validateVolume(order.getVolume());
		validateSymbol(order.getSymbol());

		long traderId = traderService.findTraderIdByUsername(username).getTraderId();
		order.setTraderId(traderId);

		// TODO: *fel* update transaction log
//		transactionService.addNewTransaction(order, )
		return orderDao.addOrder(order);
	}

	private void validateSymbol(String symbol) {
		try {
			Company company = companyService.validateCompanyByTicker(symbol);
		} catch (CompanyNotFoundException ex) {
			throw new InvalidFieldException("Symbol does not exist");
		}
	}

	public Order retrieveOrderByOrderId(long orderId) {
		return orderDao.findOrderByOrderId(orderId);
	}

	public List<Order> retrieveAllOrders() {
		// TODO: test
		return orderDao.findAllOrders();
	}

	public List<Order> retrieveOrdersByTrader(long traderId) {
		return orderDao.findOrderByTraderId(traderId);
	}

	public List<Order> retrieveOrdersByCompany(Company company) {
		String tickerSymbol = company.getTicker();
		return orderDao.findOrdersBySymbol(tickerSymbol);
	}


	public void cancelOrder(long orderId, String username) {
		Order order = orderDao.findOrderByOrderId(orderId);
		long traderId = traderService.findTraderIdByUsername(username).getTraderId();

		if(order.getTraderId() != traderId) {
			throw new UnauthorizedOperationsException("Trader does not have write access on the requested order");
		}

		if(order.getStatus() != OrderStatus.OPEN){
			throw new BadRequestException("Order is already fulfilled or cancelled");
		}

		// TODO: *fel* update transaction log
		orderDao.updateOrderStatus(orderId, OrderStatus.CANCELLED);

		return;
	}

	public Map<Object, List<Order>> retrieveOrderByCustomConditions(String groupingField, String sortingField, String order,
																	String symbolFilter, OrderSide sideFilter, OrderType typeFilter,
																	OrderStatus statusFilter) {
		if(sortingField != null) {
			sortingField = sortingField.toUpperCase();
			if(!sortingField.equals("SYMBOL") && !sortingField.equals("PRICE")) {
				throw new InvalidFieldException("Invalid value for 'sortby' field");
			}
		}

		if(order != null) {
			order = order.toUpperCase();
			if(!order.equals("ASC") && !order.equals("DESC")) {
				throw new InvalidFieldException("Invalid value for 'order' field");
			}
		}

		if(symbolFilter != null) {

		}


		List<Order> orders = orderDao.findOrdersByCustomQuery(sortingField, order, symbolFilter, sideFilter, typeFilter, statusFilter);

		Map<Object, List<Order>> groupedOrders = null;
		if(groupingField == null) {
			groupedOrders = new HashMap<>();
			groupedOrders.put("All", orders);
		} else if (groupingField.equals("side")) {
			groupedOrders = orders.stream().collect(Collectors.groupingBy(Order::getSide));
		} else if (groupingField.equals("type")) {
			groupedOrders = orders.stream().collect(Collectors.groupingBy(Order::getType));
		} else if (groupingField.equals("status")) {
			groupedOrders = orders.stream().collect(Collectors.groupingBy(Order::getStatus));
		} else {
			throw new InvalidFieldException("Invalid value for 'sortby' field");
		}
		return groupedOrders;
	}

	public void updateExistingOrder(long orderId, Order updatedOrder, String username) {
		Order existingOrder = retrieveOrderByOrderId(orderId);
		long traderId = traderService.findTraderIdByUsername(username).getTraderId();

		if(existingOrder.getTraderId() != traderId) {
			throw new UnauthorizedOperationsException("Trader does not have write access on the requested order");
		}

		if(existingOrder.getStatus() != OrderStatus.OPEN){
			throw new BadRequestException("Order is already fulfilled or cancelled");
		}

		if(!updatedOrder.getSymbol().equals(existingOrder.getSymbol())) {
			throw new BadRequestException("Updating ticker symbol is not allowed");
		}

		if(updatedOrder.getSide() != existingOrder.getSide()) {
			throw new BadRequestException("Updating side is not allowed");
		}

//		validateOrderType(updatedOrder.getType());
		validatePrice(updatedOrder.getPrice(), updatedOrder.getType());
		validateVolume(updatedOrder.getVolume());

		orderDao.updateVolumePriceAndType(orderId, updatedOrder.getVolume(), updatedOrder.getPrice(),
				updatedOrder.getType());
	}



	private void validateVolume(int volume) {
		if(volume <= 0) {
			throw new InvalidFieldException("Invalid value for 'volume' field");
		}
	}

	private void validatePrice(Double price, OrderType type) {
		if((type == OrderType.MARKET && price != null ) || (type == OrderType.LIMIT && (price == null || price <= 0))) {
			throw new InvalidFieldException("Invalid value for 'price' field");
		}
	}

	private void validateOrderType(String type) {
		if(!orderTypesSet.contains(type)) {
			throw new InvalidFieldException("Invalid value for 'type' field");
		}
	}

}



