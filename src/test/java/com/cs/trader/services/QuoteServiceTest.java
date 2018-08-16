package com.cs.trader.services;

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

import com.cs.trader.dao.QuoteDao;
import com.cs.trader.domain.Quote;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class QuoteServiceTest {
	
	@Autowired
	QuoteService service;
	
	@Test
	public void findAllQuotes() {
		List<Quote> quotes = service.findQuotes(null, null);
		assertTrue("",quotes.size() == 5);
	}
	
	@Test
	public void findQuotesByTimestamp() {
		List<Quote> quotes = service.findQuotes("2016-10-11 01:00:55.000", 
				"2016-10-11 08:00:55.000");
		System.out.println(quotes);
		assertTrue("",quotes.size() == 4);
	}
	
	@Test
	public void findQuotesByTicker() {
		List<Quote> quotes = service.findQuotesByTicker("GOOGL",null,null);
		assertTrue("",quotes.size() == 4);
	}
	
	@Test
	public void findQuotesByTickerAndTimestamp() {
		List<Quote> quotes = service.findQuotesByTicker("GOOGL", "2016-10-11 01:00:55.000",
				"2016-10-11 08:00:55.000");
		System.out.println(quotes);
		assertTrue("",quotes.size() == 4);
	}
}
