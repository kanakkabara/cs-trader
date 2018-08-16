package com.cs.trader.dao;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.cs.trader.domain.Quote;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class QuotesDaoTest {
	
	@Autowired
	QuoteDao dao;
	
	@Test
	public void findAllQuotes() {
		List<Quote> quotes = dao.findQuotes();
		assertTrue("",quotes.size() == 5);
	}
	
	@Test
	public void findQuotesByTicker() {
		List<Quote> quotes = dao.findQuotesByTicker("GOOGL");
		assertTrue("",quotes.size() == 4);
	}
	
	@Test
	public void findQuotesByTickerAndTimestamp() {
		Date timestampFrom = new Date();
		Date timestampAfter = new Date();
		List<Quote> quotes = dao.findQuotesByTickerAndTimestamp("GOOGL", timestampFrom, timestampAfter);
		assertTrue("",quotes.size() == 4);
	}
	
	@Test
	public void findQuotesByTimestamp() {
		Date timestampFrom = new Date();
		Date timestampAfter = new Date();
		List<Quote> quotes = dao.findQuotesByTimestamp(timestampFrom, timestampAfter);
		assertTrue("",quotes.size() == 4);
	}
	
	/*@Test(expected = TickerNotFoundException.class)
	public void findTraderByInvalidId() {
		Trader trader = dao.findTraderById(911);
	}*/
	
}
