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

import com.cs.trader.domain.Trader;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class TraderServiceTest {
	
	@Autowired
	TraderService service;
	
	@Test
	public void retrieveAllTraders() {
		List<Trader> traders = service.findTraders();
		for(Trader t : traders) {
			System.out.println(t);
		}
		assertTrue("Number of users found is incorrect.",traders.size() == 3);
	}
	
	@Test
	public void retrieveTraderById() {
		Trader trader = service.findTraderById(2);
		assertTrue(trader.getFirstName(), "Kevin".equals(trader.getFirstName()));
	}
	
	@Test
	public void addTrader() {
		Trader trader = new Trader("John","Smith","johns@gmail.com","6590003213","Sentosa");
		int status = service.addTrader(trader);
		assertTrue("Row not inserted successfully",status == 1);
	}
	
	@Test
	public void deleteTrader() {
		int status = service.deleteTrader(1);
		assertTrue("Row not deleted successfully", status == 1);
	}
	
}
