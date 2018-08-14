package com.cs.trader.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

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
	private OrderDao orderDao;
	
	@Test
	public void placeNewOrderForLimitType() {
		Order newOrder = new Order("IBM", "SELL", "LIMIT", 760.76, 30, new Date(), 1L);
		int status = orderDao.addOrder(newOrder);
		assertEquals(status, 1);
	}
	
	@Test
	public void placeNewOrderForMarketTypeWithNullPrice() {
		Order newOrder = new Order("IBM", "SELL", "MARKET", null, 30, new Date(), 1L);
		int status = orderDao.addOrder(newOrder);
		assertEquals(status, 1);
	}
	
}
