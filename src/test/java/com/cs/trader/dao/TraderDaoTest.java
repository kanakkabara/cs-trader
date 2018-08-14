package com.cs.trader.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cs.trader.domain.Trader;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TraderDaoTest {
	
	@Autowired
	TraderDao dao;
	
	@Test
	public void testRetrieveAllTraders() {
		List<Trader> users = dao.findTraders();
		assertTrue("Number of users found is incorrect.",users.size() == 3);
	}
	
	@Test
	public void testRetrieveTraderById() {
		Trader trader = dao.findTraderById(1);
		assertTrue(trader.getFirstName(), "Ernest".equals(trader.getFirstName()));
	}
	
	@Test
	public void addTrader() {
		Trader trader = new Trader("John","Smith","johns@gmail.com","6590003213","Sentosa");
		int status = dao.addTrader(trader);
		assertTrue("Row not inserted successfully",status == 1);
	}
	
	@Test
	public void deleteTrader() {
		int status = dao.deleteTrader(1);
		assertTrue("Row not deleted successfully", status == 1);
	}
	
}
