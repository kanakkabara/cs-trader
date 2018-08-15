package com.cs.trader.dao;

import com.cs.trader.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class OrderDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public long addOrder(String symbol, String instruction, String type, Double price, int volume, long traderId) {
		String insertSql = "INSERT INTO ORDERS(SYMBOL, INSTRUCTION, ORDER_TYPE, PRICE, VOLUME, "
				+ "PLACEMENT_TIMESTAMP, TRADER_ID, STATUS, DELETED) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
		      @Override
		      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		        final PreparedStatement ps = connection.prepareStatement(insertSql, 
		            Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, symbol);
		        ps.setString(2, instruction);
		        ps.setString(3, type);
		        if(price == null) {
		        	ps.setNull(4, Types.DOUBLE);
		        } else {
		        	ps.setDouble(4, price);
		        }
		        ps.setInt(5, volume);
		        ps.setTimestamp(6, Timestamp.from(java.time.Instant.now()));
		        ps.setLong(7, traderId);
		        ps.setString(8, "OPEN");
		        ps.setBoolean(9, false);
		        return ps;
		      }
		    }, key);

		    return key.getKey().longValue();
	}
	
	public Order findOrderByOrderId(long orderId) {
		return jdbcTemplate.queryForObject("SELECT * FROM ORDERS WHERE ORDER_ID=?", 
				new OrderRowMapper(), orderId);
	}
	
	public List<Order> findOrderByTraderId(long traderId) {
		return jdbcTemplate.query("SELECT * FROM ORDERS WHERE TRADER_ID=?", 
				new OrderRowMapper(), traderId);
	}

	public List<Order> findOrdersBySymbol(String tickerSymbol) {
		return jdbcTemplate.query("SELECT * FROM ORDERS WHERE SYMBOL=?",
				new OrderRowMapper(), tickerSymbol);
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
			order.setDeleted(rs.getBoolean("DELETED"));
			return order;
		}
	}
}
