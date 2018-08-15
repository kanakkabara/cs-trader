package com.cs.trader.dao;

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
import com.cs.trader.domain.TraderRank;
import com.cs.trader.exceptions.InvalidFieldException;
import com.cs.trader.exceptions.TraderNotFoundException;


@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class TraderDaoTest {
	
	@Autowired
	TraderDao dao;
	
	@Test
	public void findAllTraders() {
		List<Trader> traders = dao.findTraders();
		assertTrue("Number of users found is incorrect.",traders.size() == 3);
	}
	
	@Test
	public void findTraderById() {
		Trader trader = dao.findTraderById(1);
		assertTrue(trader.getFirstName(), "Ernest".equals(trader.getFirstName()));
	}
	
	
	@Test(expected = TraderNotFoundException.class)
	public void findTraderByInvalidId() {
		Trader trader = dao.findTraderById(911);
	}
	
	@Test
	public void addTrader() {
		Trader trader = new Trader("John","Smith","johns@gmail.com","6590003213","Sentosa");
		int status = dao.addTrader(trader);
		assertTrue("Row not inserted successfully",status == 1);
	}
	
	@Test(expected = InvalidFieldException.class)
	public void addTraderWithInvalidFields() {
		Trader trader = new Trader("John",null,"johns@gmail.com","6590003213","Sentosa");
		int status = dao.addTrader(trader);
	}
	
	@Test
	public void deleteTrader() {
		int status = dao.deleteTrader(2);
		assertTrue("Row not deleted successfully", status == 1);
	}
	
	@Test
	public void findTopFiveTradersByNumTrades() {
		List<TraderRank> traders = dao.findTopFiveTradersByNumTrades();
		Trader firstTrader = traders.get(0).getTrader();
		System.out.println(traders);
		assertTrue(firstTrader.getFirstName().equals("Ernest"));
	}
	
	@Test
	public void findTopFiveTradersByVolume() {
		List<TraderRank> traders = dao.findTopFiveTradersByVolume();
		Trader firstTrader = traders.get(0).getTrader();
		System.out.println(traders);
		assertTrue(firstTrader.getFirstName().equals("Ernest"));
	}
	
}
