package com.cs.trader.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cs.trader.domain.Order;

@Repository
public class OrderDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int addOrder(Order order) {
		String sql = "INSERT INTO ORDERS(SYMBOL, INSTRUCTION, ORDER_TYPE, PRICE, VOLUME, "
				+ "PLACEMENT_TIMESTAMP, TRADER_ID) VALUES(?, ?, ?, ?, ?, ?, ?);";
		int status = jdbcTemplate.update(sql,
				new Object[] { order.getSymbol(), order.getInstruction(), order.getOrderType(), order.getPrice(),
						order.getVolume(), order.getPlacementTimestamp(), order.getTraderId()});
		return status;
	}
}
