package com.cs.trader.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cs.trader.CsTraderApplication;
import com.cs.trader.domain.Order;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
public class OrderDaoTest {
	@Autowired
	OrderDao orderDao;
	
	@Test
	public void onAddingAnyRequestType() {
		Timestamp startTs = Timestamp.from(java.time.Instant.now());
		long orderId = orderDao.addOrder("TEST1-1", "SELL", "LIMIT", new Double(760.76), 30, 1L);
		Timestamp endTs = Timestamp.from(java.time.Instant.now());
		assertThat("should return order id", orderId, is(greaterThan(0L)));
		
		Order createdOrder = orderDao.findOrderByOrderId(orderId);
		assertThat("should generate order placement timestamp", createdOrder.getPlacementTimestamp(), 
				is(both(greaterThanOrEqualTo(startTs)).and(lessThanOrEqualTo(endTs))));
	}
	
	@Test
	public void onAddingMarketTypeRequest() {
		long orderId = orderDao.addOrder("TEST2", "SELL", "MARKET", null, 30, 1L);
		assertThat("should allow inserting null value as price", orderId, is(greaterThan(0L)));
	}
	
	@Test
	public void onFindingOrderById() {
		// TODO: delete and recreate database, insert order, retrieve id 1 
	}
}
