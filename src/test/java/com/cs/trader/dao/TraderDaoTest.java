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
		for(Trader t : traders) {
			System.out.println(t);
		}
		assertTrue("Number of users found is incorrect.",traders.size() == 3);
	}
	
	@Test
	public void findTraderById() {
		Trader trader = dao.findTraderById(2);
		assertTrue(trader.getFirstName(), "Kevin".equals(trader.getFirstName()));
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
		int status = dao.deleteTrader(1);
		assertTrue("Row not deleted successfully", status == 1);
	}
	
}
