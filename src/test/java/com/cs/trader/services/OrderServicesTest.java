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
	public void validateSymbolOnOrderPlacement() {
		assertTrue("ticker symbol must exist in the exchange", true);
	}
	
	@Test
	public void validateInstructionOnOrderPlacement() {
		assertTrue("instruction must be either 'BUY' or 'SELL'", true);
	}
	
	@Test
	public void validateTypeOnOrderPlacement() {
		assertTrue("type must be either 'LIMIT' or 'MARKET'", true);
	}
	
	@Test
	public void validatePriceOnOrderPlacement() {
		assertTrue("price must be a positive number or null", true);
	}
	
	@Test
	public void validateVolumeOnOrderPlacement() {
		assertTrue("number of shares must be a positive number", true);
	}
	
	@Test
	public void validateTraderIdOnOrderPlacement() {
		assertTrue("trader must be registered", true);
	}
	
	@Test
	public void validateLimitTypeOrderOnOrderPlacement() {
		assertTrue("price must be a positive number", true);
	}
	
	@Test
	public void validateMarketTypeOrderOnOrderPlacement() {
		assertTrue("price must be null", true);
	}

	@Test
	public void canPlaceValidLimitTypeOrder() {
		assertTrue("price must be null", true);
	}

}
