package com.cs.trader.dao;

import com.cs.trader.CsTraderApplication;
import com.cs.trader.domain.Order;
import com.cs.trader.domain.OrderSide;
import com.cs.trader.domain.OrderStatus;
import com.cs.trader.domain.OrderType;
import com.cs.trader.exceptions.OrderNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
@Transactional
public class OrderDaoTest {
	@Autowired
	OrderDao orderDao;
	
	@Test
	public void onAddingAnyRequestType() {
		Order order = new Order("TEST1", OrderSide.SELL, OrderType.LIMIT, 760.76, 30);
		Timestamp startTs = Timestamp.from(java.time.Instant.now());
		long orderId = orderDao.addOrder(order);
		Timestamp endTs = Timestamp.from(java.time.Instant.now());
		assertThat("should return order id", orderId, is(greaterThan(0L)));
		
		Order createdOrder = orderDao.findOrderByOrderId(orderId);
		assertThat("should generate order placement timestamp", createdOrder.getPlacementTimestamp(), 
				is(both(greaterThanOrEqualTo(startTs)).and(lessThanOrEqualTo(endTs))));
	}
	
	@Test
	public void onAddingMarketTypeRequest() {
		Order order = new Order("TEST2", OrderSide.SELL, OrderType.MARKET, null, 30);
		long orderId = orderDao.addOrder(order);
		assertThat("should allow inserting null value as price", orderId, is(greaterThan(0L)));
	}
	
	@Test
	public void onFindingExistingOrderById() {
		// TODO: delete and recreate database, insert order, retrieve id 1 
	}

	@Test(expected = OrderNotFoundException.class)
	public void onFindingInvalidOrderById() {
		long nonExistentOrderId = 100l;
		Order order = orderDao.findOrderByOrderId(nonExistentOrderId);
	}

	@Test
	public void customFindQueryUsingInstructionFilter() {
		List<Order> result = orderDao.findOrdersByCustomQuery(null, null, null, OrderSide.BUY, null, null);
		assertEquals(10, result.size());
	}

	@Test
	public void customFindQueryUsingTypeFilter() {
		List<Order> result = orderDao.findOrdersByCustomQuery(null, null, null, null, OrderType.MARKET, null);
		assertEquals(2, result.size());
	}

	@Test
	public void customFindQueryUsingStatusFilter() {
		List<Order> result = orderDao.findOrdersByCustomQuery(null, null, null, null, null, OrderStatus.CANCELLED);
		assertEquals(1, result.size());
	}

	@Test
	public void customFindQueryUsingSortBySymbol() {
		List<Order> result = orderDao.findOrdersByCustomQuery("SYMBOL", null, null, null, null, null);
		assertEquals(12, result.size());
		assertEquals("COMP1", result.get(0).getSymbol());
		assertEquals("GOOGL", result.get(result.size() - 1).getSymbol());
	}

	@Test
	public void customFindQueryUsingSortByDescending() {
		List<Order> result = orderDao.findOrdersByCustomQuery("SYMBOL", "DESC", null, null, null, null);
		assertEquals(12, result.size());
		assertEquals("GOOGL", result.get(0).getSymbol());
		assertEquals("COMP1", result.get(result.size() - 1).getSymbol());
	}

	@Test
	public void customFindQueryUsingSortByPrice() {
		List<Order> result = orderDao.findOrdersByCustomQuery("PRICE", "DESC", null, null, null, null);
		assertEquals(12, result.size());
		assertThat(result.get(0).getPrice(), is(greaterThan(result.get(result.size() - 1).getPrice())));
	}

	@Test
	public void onUpdatingOrderStatus() {
		long orderId = 1;
		OrderStatus newStatus = OrderStatus.CANCELLED;
		int response = orderDao.updateOrderStatus(orderId, newStatus);
		Order affectedOrder = orderDao.findOrderByOrderId(orderId);
		assertEquals("affected order must reflect the new status", newStatus, affectedOrder.getStatus());
	}
}
