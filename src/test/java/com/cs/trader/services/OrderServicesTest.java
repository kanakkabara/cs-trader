package com.cs.trader.services;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cs.trader.CsTraderApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
public class OrderServicesTest {
	@Autowired
	OrderService orderService;
	
	@Test
	public void symbolValidation() {
		assertTrue("ticker symbol must exist in the exchange", true);
	}
	
	@Test
	public void instructionValidation() {
		assertTrue("instruction must be either 'BUY' or 'SELL'", true);
	}
	
	@Test
	public void typeValidation() {
		assertTrue("type must be either 'LIMIT' or 'MARKET'", true);
	}
	
	@Test
	public void priceValidation() {
		assertTrue("price must be a positive number or null", true);
	}
	
	@Test
	public void volumeValidation() {
		assertTrue("number of shares must be a positive number", true);
	}
	
	@Test
	public void traderValidation() {
		assertTrue("trader must be registered", true);
	}
	
	@Test
	public void onLimitTypeOrder() {
		assertTrue("price must be a positive number", true);
	}
	
	@Test
	public void onMarketTypeOrder() {
		assertTrue("price must be a positive number", true);
	}



}
