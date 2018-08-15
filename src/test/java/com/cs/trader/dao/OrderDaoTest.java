package com.cs.trader.dao;

import com.cs.trader.CsTraderApplication;
import com.cs.trader.domain.Order;
import com.cs.trader.exceptions.OrderNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
public class OrderDaoTest {
	@Autowired
	OrderDao orderDao;
	
	@Test
	public void onAddingAnyRequestType() {
		Order order = new Order("TEST1", "SELL", "LIMIT", 760.76, 30, 1L);
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
		Order order = new Order("TEST2", "SELL", "MARKET", null, 30, 1L);
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
	public void onUpdatingOrderStatus() {
		long orderId = 1;
		String newStatus = "CANCELLED";
		int response = orderDao.setOrderStatus(orderId, newStatus);
		Order affectedOrder = orderDao.findOrderByOrderId(orderId);
		assertEquals("affected order must reflect the new status", newStatus, affectedOrder.getStatus());
	}
}
