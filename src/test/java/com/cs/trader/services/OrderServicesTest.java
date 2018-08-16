package com.cs.trader.services;

import com.cs.trader.CsTraderApplication;
import com.cs.trader.domain.Order;
import com.cs.trader.domain.OrderSide;
import com.cs.trader.domain.OrderType;
import com.cs.trader.exceptions.InvalidFieldException;
import com.cs.trader.exceptions.OrderNotFoundException;
import com.cs.trader.exceptions.TraderNotFoundException;
import com.cs.trader.exceptions.UnauthorizedOperationsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
@Transactional
public class OrderServicesTest {
	@Autowired
	OrderService orderService;

	@Test
	public void canPlaceLimitTypeOrder() {
		Order order = new Order("COMP2", OrderSide.BUY, OrderType.LIMIT, 100.04, 10);
		long orderId = orderService.placeNewOrder(order, "ernest");
		assertThat("should allow valid limit type order", orderId, is(greaterThan(0L)));
	}

	@Test
	public void canPlaceMarketTypeOrder() {
		Order order = new Order("COMP2", OrderSide.BUY, OrderType.MARKET, null, 10);
		long orderId = orderService.placeNewOrder(order, "ernest");
		assertThat("should allow valid market type order", orderId, is(greaterThan(0L)));
	}

	@Test(expected = InvalidFieldException.class)
	public void validateSymbolOnOrderPlacement() {
		Order order = new Order("XXXX", OrderSide.BUY, OrderType.MARKET, null, 10);
		long orderId = orderService.placeNewOrder(order, "ernest");
	}

//	@Test(expected = InvalidFieldException.class)
//	public void validateInstructionOnOrderPlacement() {
//		Order order = new Order("COMP2", "XXXX", OrderType.MARKET, null, 10);
//		long orderId = orderService.placeNewOrder(order, "ernest");
//	}

//	@Test(expected = InvalidFieldException.class)
//	public void validateTypeOnOrderPlacement() {
//		Order order = new Order("COMP2", OrderSide.BUY, "XXXX", null, 10);
//		long orderId = orderService.placeNewOrder(order, "ernest");
//	}

	@Test(expected = InvalidFieldException.class)
	public void validatePriceOnOrderPlacementWithNegativeValue() {
		Order order = new Order("COMP2", OrderSide.BUY, OrderType.LIMIT, -10.04, 10);
		long orderId = orderService.placeNewOrder(order, "ernest");
	}

	@Test(expected = InvalidFieldException.class)
	public void validatePriceOnOrderPlacementWithZeroValue() {
		Order order = new Order("COMP2", OrderSide.BUY, OrderType.LIMIT, 0d, 10);
		long orderId = orderService.placeNewOrder(order, "ernest");
	}

	@Test(expected = InvalidFieldException.class)
	public void validateVolumeOnOrderPlacementWithNegativeValue() {
		Order order = new Order("COMP2", OrderSide.BUY, OrderType.LIMIT, 10.04, -10);
		long orderId = orderService.placeNewOrder(order, "ernest");
	}

	@Test(expected = InvalidFieldException.class)
	public void validateVolumeOnOrderPlacementWithZeroValue() {
		Order order = new Order("COMP2", OrderSide.BUY, OrderType.LIMIT, 10.04, 0);
		long orderId = orderService.placeNewOrder(order, "ernest");
	}

	@Test(expected = TraderNotFoundException.class)
	public void validateTraderIdOnOrderPlacement() {
		Order order = new Order("COMP2", OrderSide.BUY, OrderType.LIMIT, 10.04, 10);
		long orderId = orderService.placeNewOrder(order, "voldemort");
	}

	@Test(expected = InvalidFieldException.class)
	public void validateLimitTypeOrderOnOrderPlacement() {
		Order order = new Order("COMP2", OrderSide.BUY, OrderType.LIMIT, null, 10);
		long orderId = orderService.placeNewOrder(order, "ernest");
	}

	@Test(expected = InvalidFieldException.class)
	public void validateMarketTypeOrderOnOrderPlacement() {
		Order order = new Order("COMP2", OrderSide.BUY, OrderType.MARKET, 10.04, 10);
		long orderId = orderService.placeNewOrder(order, "ernest");
	}


	@Test(expected = OrderNotFoundException.class)
	public void cannotCancelOrderThatDoesNotExist() {
		orderService.cancelOrder(100l, "ernest");
	}

	@Test(expected = UnauthorizedOperationsException.class)
	public void cannotCancelOrderCreatedByOtherTrader() {
		orderService.cancelOrder(1l, "admin");
	}

	@Test(expected = BadRequestException.class)
	public void cannotCancelOrderWithFulfilledStatus() {
		orderService.cancelOrder(2l, "ernest");
	}

	@Test(expected = BadRequestException.class)
	public void cannotCancelOrderWithCancelledStatus() {
		orderService.cancelOrder(8l, "ernest");
	}

	@Test
	public void canCancelOrderWithOpenStatus() {
		orderService.cancelOrder(1l, "ernest");
	}


	@Test(expected = OrderNotFoundException.class)
	public void cannotUpdateOrderThatDoesNotExist() {
		Order updatedOrder = new Order("COMP1", OrderSide.BUY, OrderType.LIMIT, 110.03, 415);
		orderService.updateExistingOrder(100l, updatedOrder, "ernest");
	}

	@Test(expected = UnauthorizedOperationsException.class)
	public void cannotUpdateOrderCreatedByOtherTrader() {
		Order updatedOrder = new Order("COMP1", OrderSide.BUY, OrderType.LIMIT, 110.03, 415);
		orderService.updateExistingOrder(1l, updatedOrder, "admin");
	}

	@Test(expected = BadRequestException.class)
	public void cannotUpdateOrderWithFulfilledStatus() {
		Order updatedOrder = new Order("GOOGL", OrderSide.BUY, OrderType.LIMIT, 760.76, 30);
		orderService.updateExistingOrder(2l, updatedOrder, "ernest");
	}

	@Test(expected = BadRequestException.class)
	public void cannotUpdateOrderWithCancelledStatus() {
		Order updatedOrder = new Order("COMP1", OrderSide.BUY, OrderType.LIMIT, 110.03, 415);
		orderService.updateExistingOrder(9l, updatedOrder, "ernest");
	}

	@Test(expected = BadRequestException.class)
	public void cannotUpdateTickerSymbol() {
		Order updatedOrder = new Order("COMP2", OrderSide.BUY, OrderType.LIMIT, 110.03, 415);
		orderService.updateExistingOrder(1l, updatedOrder, "ernest");
	}

	@Test(expected = BadRequestException.class)
	public void cannotUpdateSide() {
		Order updatedOrder = new Order("COMP1", OrderSide.SELL, OrderType.LIMIT, 110.03, 415);
		orderService.updateExistingOrder(1l, updatedOrder, "ernest");
	}

	@Test
	public void canUpdateVolumePriceAndTypeOnOrderWithOpenStatus() {
		Order updatedOrder = new Order("COMP1", OrderSide.BUY, OrderType.MARKET, null, 10);
		orderService.updateExistingOrder(1l, updatedOrder, "ernest");
	}


	@Test
	public void canRetrieveOrdersWithoutGrouping() {
		Map<Object, List<Order>> result = orderService.retrieveOrderByCustomConditions(null, null,
				null, null, null, null, null);
		assertTrue(result.containsKey("All"));
	}

	@Test
	public void canRetrieveOrdersGroupedBySide() {
		Map<Object, List<Order>> result = orderService.retrieveOrderByCustomConditions("side", null,
				null, null, null, null, null);
		assertTrue(result.containsKey(OrderSide.BUY));
		assertTrue(result.containsKey(OrderSide.SELL));
	}

	@Test
	public void canRetrieveOrdersGroupedByType() {
		Map<Object, List<Order>> result = orderService.retrieveOrderByCustomConditions("type", null,
				null, null, null, null, null);
		assertTrue(result.containsKey(OrderType.MARKET));
		assertTrue(result.containsKey(OrderType.LIMIT));
	}

	@Test
	public void canRetrieveOrdersGroupedByStatus() {
		Map<Object, List<Order>> result = orderService.retrieveOrderByCustomConditions("status", null,
				null, null, null, null, null);
		assertTrue(result.containsKey("OPEN"));
		assertTrue(result.containsKey("CANCELLED"));
		assertTrue(result.containsKey("FULFILLED"));
	}

	@Test(expected = InvalidFieldException.class)
	public void throwErrorOnRetrieveOrdersGroupedByInvalidField() {
		Map<Object, List<Order>> result = orderService.retrieveOrderByCustomConditions("alien", null,
				null, null, null, null, null);
	}
}
