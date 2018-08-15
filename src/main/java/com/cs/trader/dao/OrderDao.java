package com.cs.trader.dao;

<<<<<<< HEAD
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

=======
import com.cs.trader.domain.Order;
>>>>>>> branch 'master' of https://github.com/kanakkabara/cs-trader.git
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import com.cs.trader.domain.ActivitySummary;
import com.cs.trader.domain.Order;
=======
import java.sql.*;
import java.util.List;
>>>>>>> branch 'master' of https://github.com/kanakkabara/cs-trader.git

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
	
	public ActivitySummary findActivitySummaryByTraderId(long traderId) {
		String sql = "SELECT R.LATEST_ORDER, STATUS, ORDER_COUNT FROM (SELECT MAX(PLACEMENT_TIMESTAMP) AS LATEST_ORDER, TRADER_ID FROM ORDERS WHERE TRADER_ID = ?) R" + 
				"INNER JOIN (SELECT COUNT(ORDER_ID) AS ORDER_COUNT, STATUS FROM ORDERS WHERE TRADER_ID = ? GROUP BY STATUS) WHERE R.TRADER_ID = TRADER_ID";
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql, traderId, traderId);
		ActivitySummary summary = new ActivitySummary();
		Map<String,Integer> orders = new HashMap<String, Integer>();
		for (Map row : rows) {
			summary.setLastOrderPlacement((Timestamp)row.get("LATEST_ORDER"));
			orders.put(row.get("STATUS").toString(), (Integer)row.get("ORDER_COUNT"));
		}
		return summary;
	}
}
