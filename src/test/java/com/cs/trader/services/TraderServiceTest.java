package com.cs.trader.services;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.cs.trader.domain.ActivitySummary;
import com.cs.trader.domain.Order;
import com.cs.trader.domain.Trader;
import com.cs.trader.domain.TraderRank;
import com.cs.trader.exceptions.InvalidFieldException;
import com.cs.trader.exceptions.TraderNotFoundException;
import com.cs.trader.exceptions.TraderStillWorkingException;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class TraderServiceTest {
	
	@Autowired
	TraderService service;
	
	@Test
	public void findAllTraders() {
		List<Trader> traders = service.findTraders();
		assertTrue("Number of users found is incorrect.",traders.size() == 3);
	}
	
	@Test
	public void findTraderById() {
		Trader trader = service.findTraderById(1);
		assertTrue(trader.getFirstName(), "Ernest".equals(trader.getFirstName()));
	}
	
	@Test(expected = TraderNotFoundException.class)
	public void findTraderByInvalidId() {
		Trader trader = service.findTraderById(911);
	}
	
	@Test
	public void addTrader() {
		Trader trader = new Trader("John","Smith","johns@gmail.com","6590003213","Sentosa","johnny");
		long traderId = service.addTrader(trader);
		System.out.println(traderId);
	}
	
	@Test(expected = InvalidFieldException.class)
	public void addTraderWithInvalidFields() {
		Trader trader = new Trader("John",null,"johns@gmail.com","6590003213","Sentosa","johnny");
		long traderId = service.addTrader(trader);
		
	}
	
	@Test
	public void deleteTrader() {
		int status = service.deleteTrader(2);
		assertTrue("Row not deleted successfully", status == 1);
	}
	
	@Test(expected = TraderStillWorkingException.class)
	public void deleteWorkingTrader() {
		int status = service.deleteTrader(1);
	}
	
	@Test (expected = TraderNotFoundException.class)
	public void deleteInvalidTrader() {
		int status = service.deleteTrader(999);
	}
	
	@Test
	public void findActivitySummaryByTraderId() {
		ActivitySummary summary = service.findActivitySummaryByTraderId(1);
		assertTrue("", summary.getOrders().get("OPEN") == 6);
	}
	
	@Test(expected = TraderNotFoundException.class)
	public void findActivitySummaryByInvalidTraderId() {
		ActivitySummary summary = service.findActivitySummaryByTraderId(999);
		assertTrue("", summary.getOrders().get("OPEN") == 6);
	}
	
	@Test
	public void findOrdersByTraderId() {
		List<Order> orders = service.findOrdersByTraderId(1);
		assertTrue("", orders.size() == 10);
	}
	
	@Test(expected = TraderNotFoundException.class)
	public void findOrdersByInvalidTraderId() {
		List<Order> orders = service.findOrdersByTraderId(999);
		System.out.println(orders);
	}
	
	@Test
	public void findTopFiveTradersByNumTrades() {
		List<TraderRank> traders = service.findTopFiveTradersByNumTrades();
		Trader firstTrader = traders.get(0).getTrader();
		System.out.println(traders);
		assertTrue(firstTrader.getFirstName().equals("Ernest"));
	}
	
	@Test
	public void findTopFiveTradersByVolume() {
		List<TraderRank> traders = service.findTopFiveTradersByVolume();
		Trader firstTrader = traders.get(0).getTrader();
		System.out.println(traders);
		assertTrue(firstTrader.getFirstName().equals("Ernest"));
	}
	
}
