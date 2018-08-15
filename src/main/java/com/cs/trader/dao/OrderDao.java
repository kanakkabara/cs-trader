package com.cs.trader.dao;

import com.cs.trader.domain.ActivitySummary;
import com.cs.trader.domain.Order;
import com.cs.trader.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public long addOrder(Order order) {
		String insertSql = "INSERT INTO ORDERS(SYMBOL, INSTRUCTION, ORDER_TYPE, PRICE, VOLUME, "
				+ "PLACEMENT_TIMESTAMP, TRADER_ID, STATUS) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
		      @Override
		      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		        final PreparedStatement ps = connection.prepareStatement(insertSql, 
		            Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, order.getSymbol());
		        ps.setString(2, order.getInstruction());
		        ps.setString(3, order.getOrderType());
		        if(order.getPrice() == null) {
		        	ps.setNull(4, Types.DOUBLE);
		        } else {
		        	ps.setDouble(4, order.getPrice());
		        }
		        ps.setInt(5, order.getVolume());
		        ps.setTimestamp(6, Timestamp.from(java.time.Instant.now()));
		        ps.setLong(7, order.getTraderId());
		        ps.setString(8, "OPEN");
		        return ps;
		      }
		    }, key);

		    return key.getKey().longValue();
	}
	
	public Order findOrderByOrderId(long orderId) {
		try{
			return jdbcTemplate.queryForObject("SELECT * FROM ORDERS WHERE ORDER_ID=?",
					new OrderRowMapper(), orderId);
		} catch(Exception ex) {
			throw new OrderNotFoundException("Order with id " + orderId + " cannot be found.");
		}
	}
	
	public List<Order> findOrderByTraderId(long traderId) {
		return jdbcTemplate.query("SELECT * FROM ORDERS WHERE TRADER_ID=?", 
				new OrderRowMapper(), traderId);
	}

	public List<Order> findOrdersBySymbol(String tickerSymbol) {
		return jdbcTemplate.query("SELECT * FROM ORDERS WHERE SYMBOL=?",
				new OrderRowMapper(), tickerSymbol);
	}

	public int setOrderStatus(long orderId, String newStatus) {

		int numOfRowAffected = jdbcTemplate.update("UPDATE ORDERS SET STATUS=? WHERE ORDER_ID=?",
				new Object[] {newStatus, orderId});
		return numOfRowAffected;
	}

	class OrderRowMapper implements RowMapper<Order>
	{
		@Override
		public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
			Order order = new Order();
			order.setOrderId(rs.getLong("ORDER_ID"));
			order.setSymbol(rs.getString("SYMBOL"));
			order.setInstruction(rs.getString("INSTRUCTION"));
			order.setOrderType(rs.getString("ORDER_TYPE"));
			order.setPrice(rs.getDouble("PRICE"));
			order.setVolume(rs.getInt("VOLUME"));
			order.setPlacementTimestamp(rs.getTimestamp("PLACEMENT_TIMESTAMP"));
			order.setTraderId(rs.getLong("TRADER_ID"));
			order.setStatus(rs.getString("STATUS"));
			return order;
		}
	}

	public ActivitySummary findActivitySummaryByTraderId(long traderId) {
		String sql = "SELECT R.LATEST_ORDER, STATUS, ORDER_COUNT FROM (SELECT MAX(PLACEMENT_TIMESTAMP) AS LATEST_ORDER, TRADER_ID FROM ORDERS WHERE TRADER_ID = ?) R" +
				" INNER JOIN (SELECT COUNT(ORDER_ID) AS ORDER_COUNT, STATUS FROM ORDERS WHERE TRADER_ID = ? GROUP BY STATUS) WHERE R.TRADER_ID = TRADER_ID";
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql, traderId, traderId);
		ActivitySummary summary = new ActivitySummary();
		Map<String,Long> orders = new HashMap<String, Long>();
		for (Map row : rows) {
			summary.setLastOrderPlacement((Timestamp)row.get("LATEST_ORDER"));
			orders.put(row.get("STATUS").toString(), (Long)row.get("ORDER_COUNT"));
		}
		summary.setOrders(orders);
		return summary;
	}
}
