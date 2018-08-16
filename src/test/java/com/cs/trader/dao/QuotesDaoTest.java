package com.cs.trader.dao;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Calendar;
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
		Calendar cal = Calendar.getInstance();	
		cal.set(116 + 1900, 9, 11, 1, 0, 0);
		Date timestampFrom = cal.getTime();
		cal.set(116 + 1900, 9, 11, 8, 0, 0);
		System.out.println(new Timestamp(timestampFrom.getTime()));
		Date timestampAfter = cal.getTime();
		List<Quote> quotes = dao.findQuotesByTickerAndTimestamp("GOOGL", timestampFrom, timestampAfter);
		System.out.println(quotes);
		assertTrue("",quotes.size() == 4);
	}
	
	@Test
	public void findQuotesByTimestamp() {
		Calendar cal = Calendar.getInstance();	
		cal.set(116 + 1900, 9, 11, 1, 0, 0);
		Date timestampFrom = cal.getTime();
		cal.set(116 + 1900, 9, 11, 8, 0, 0);
		Date timestampAfter = cal.getTime();
		List<Quote> quotes = dao.findQuotesByTimestamp(timestampFrom, timestampAfter);
		System.out.println(quotes);
		assertTrue("",quotes.size() == 4);
	}
	
	/*@Test(expected = TickerNotFoundException.class)
	public void findTraderByInvalidId() {
		Trader trader = dao.findTraderById(911);
	}*/
	
}
