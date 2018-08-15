package com.cs.trader.services;

import com.cs.trader.CsTraderApplication;
import com.cs.trader.domain.Order;
import com.cs.trader.exceptions.InvalidFieldException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
public class OrderServicesTest {
	@Autowired
	OrderService orderService;

	@Test
	public void canPlaceLimitTypeOrder() {
		Order order = new Order("COMP2", "BUY", "LIMIT", 100.04, 10, 1L);
		long orderId = orderService.placeNewOrder(order);
		assertThat("should allow valid limit type order", orderId, is(greaterThan(0L)));
	}

	@Test
	public void canPlaceMarketTypeOrder() {
		Order order = new Order("COMP2", "BUY", "MARKET", null, 10, 1L);
		long orderId = orderService.placeNewOrder(order);
		assertThat("should allow valid market type order", orderId, is(greaterThan(0L)));
	}

	@Test(expected = InvalidFieldException.class)
	public void validateSymbolOnOrderPlacement() {
		Order order = new Order("XXXX", "BUY", "MARKET", null, 10, 1L);
		long orderId = orderService.placeNewOrder(order);
	}

	@Test(expected = InvalidFieldException.class)
	public void validateInstructionOnOrderPlacement() {
		Order order = new Order("COMP2", "XXXX", "MARKET", null, 10, 1L);
		long orderId = orderService.placeNewOrder(order);
	}

	@Test(expected = InvalidFieldException.class)
	public void validateTypeOnOrderPlacement() {
		Order order = new Order("COMP2", "BUY", "XXXX", null, 10, 1L);
		long orderId = orderService.placeNewOrder(order);
	}

	@Test(expected = InvalidFieldException.class)
	public void validatePriceOnOrderPlacementWithNegativeValue() {
		Order order = new Order("COMP2", "BUY", "LIMIT", -10.04, 10, 1L);
		long orderId = orderService.placeNewOrder(order);
	}

	@Test(expected = InvalidFieldException.class)
	public void validatePriceOnOrderPlacementWithZeroValue() {
		Order order = new Order("COMP2", "BUY", "LIMIT", 0d, 10, 1L);
		long orderId = orderService.placeNewOrder(order);
	}

	@Test(expected = InvalidFieldException.class)
	public void validateVolumeOnOrderPlacementWithNegativeValue() {
		Order order = new Order("COMP2", "BUY", "LIMIT", 10.04, -10, 1L);
		long orderId = orderService.placeNewOrder(order);
	}

	@Test(expected = InvalidFieldException.class)
	public void validateVolumeOnOrderPlacementWithZeroValue() {
		Order order = new Order("COMP2", "BUY", "LIMIT", 10.04, 0, 1L);
		long orderId = orderService.placeNewOrder(order);
	}

	@Test(expected = InvalidFieldException.class)
	public void validateTraderIdOnOrderPlacement() {
		Order order = new Order("COMP2", "BUY", "LIMIT", 10.04, 10, 100L);
		long orderId = orderService.placeNewOrder(order);
	}

	@Test(expected = InvalidFieldException.class)
	public void validateLimitTypeOrderOnOrderPlacement() {
		Order order = new Order("COMP2", "BUY", "LIMIT", null, 10, 1L);
		long orderId = orderService.placeNewOrder(order);
	}

	@Test(expected = InvalidFieldException.class)
	public void validateMarketTypeOrderOnOrderPlacement() {
		Order order = new Order("COMP2", "BUY", "MARKET", 10.04, 10, 1L);
		long orderId = orderService.placeNewOrder(order);
	}

}
