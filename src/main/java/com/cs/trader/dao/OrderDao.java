package com.cs.trader.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cs.trader.domain.ActivitySummary;
import com.cs.trader.domain.Order;
import com.cs.trader.domain.OrderSide;
import com.cs.trader.domain.OrderStatus;
import com.cs.trader.domain.OrderType;
import com.cs.trader.exceptions.OrderNotFoundException;
import com.cs.trader.exceptions.TraderNotFoundException;

@Repository
public class OrderDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public long addOrder(Order order) {
		String insertSql = "INSERT INTO ORDERS(SYMBOL, SIDE, TYPE, PRICE, VOLUME, "
				+ "PLACEMENT_TIMESTAMP, TRADER_ID, STATUS) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
		      @Override
		      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		        final PreparedStatement ps = connection.prepareStatement(insertSql,
		            Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1, order.getSymbol());
		        ps.setString(2, order.getSide().toString());
		        ps.setString(3, order.getType().toString());
		        if(order.getPrice() == null) {
		        	ps.setNull(4, Types.DOUBLE);
		        } else {
		        	ps.setDouble(4, order.getPrice());
		        }
		        ps.setInt(5, order.getVolume());
		        ps.setTimestamp(6, Timestamp.from(java.time.Instant.now()));
		        ps.setLong(7, order.getTraderId());
		        ps.setString(8, OrderStatus.OPEN.toString());
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

	public List<Order> findAllOrders() {
		return jdbcTemplate.query("SELECT * FROM ORDERS", new OrderRowMapper());
	}

	public List<Order> findOrderByTraderId(long traderId) {
		return jdbcTemplate.query("SELECT * FROM ORDERS WHERE TRADER_ID=?",
				new OrderRowMapper(), traderId);
	}

	public List<Order> findOrdersBySymbol(String tickerSymbol) {
		return jdbcTemplate.query("SELECT * FROM ORDERS WHERE SYMBOL=?",
				new OrderRowMapper(), tickerSymbol);
	}

	public int updateOrderStatus(long orderId, OrderStatus newStatus) {
		int numOfRowAffected = jdbcTemplate.update("UPDATE ORDERS SET STATUS=? WHERE ORDER_ID=?",
				new Object[] {newStatus.toString(), orderId});
		return numOfRowAffected;
	}

	public int updateVolumePriceAndType(long orderId, int volume, Double price, OrderType type) {
		int numOfRowAffected = jdbcTemplate.update("UPDATE ORDERS SET VOLUME=?, PRICE=?, TYPE=?  " +
						"WHERE ORDER_ID=?", new Object[] {volume, price, type.toString(), orderId});
		return numOfRowAffected;
	}

	public List<Order> findOrdersByCustomQuery(String sortingField, String order, String symbolFilter,
											   OrderSide sideFilter, OrderType typeFilter, OrderStatus statusFilter) {
		String sql = "SELECT * FROM ORDERS";

		List<String> filterList = new ArrayList<>();
		List<Object> paramValueList= new ArrayList<Object>();

		if(symbolFilter != null) {
			filterList.add("SYMBOL=?");
			paramValueList.add(symbolFilter);
		}
		if(sideFilter != null) {
			filterList.add("SIDE=?");
			paramValueList.add(sideFilter.toString());
		}
		if(typeFilter != null) {
			filterList.add("TYPE=?");
			paramValueList.add(typeFilter.toString());
		}
		if(statusFilter != null) {
			filterList.add("STATUS=?");
			paramValueList.add(statusFilter.toString());
		}

		if(!filterList.isEmpty()) {
			String joinedFilterList = String.join(" AND ", filterList);
			sql = sql + " WHERE " + joinedFilterList;
			System.out.println(sql);
		}

		if(sortingField != null) {
			sql += " ORDER BY " + sortingField;
			if(order != null) {
				sql = sql + " " + order;
			}
		}

		return jdbcTemplate.query(sql, paramValueList.toArray(), new OrderRowMapper());
	}

	class OrderRowMapper implements RowMapper<Order>
	{
		@Override
		public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
			Order order = new Order();
			order.setOrderId(rs.getLong("ORDER_ID"));
			order.setSymbol(rs.getString("SYMBOL"));
			order.setSide(OrderSide.valueOf(rs.getString("SIDE")));
			order.setType(OrderType.valueOf(rs.getString("TYPE")));
			order.setPrice(rs.getDouble("PRICE"));
			order.setVolume(rs.getInt("VOLUME"));
			order.setPlacementTimestamp(rs.getTimestamp("PLACEMENT_TIMESTAMP"));
			order.setTraderId(rs.getLong("TRADER_ID"));
			order.setStatus(OrderStatus.valueOf(rs.getString("STATUS")));
			return order;
		}
	}

	public ActivitySummary findActivitySummaryByTraderId(long traderId) {
		String sql = "SELECT R.LATEST_ORDER, STATUS, ORDER_COUNT FROM (SELECT MAX(PLACEMENT_TIMESTAMP) AS LATEST_ORDER, TRADER_ID FROM ORDERS WHERE TRADER_ID = ?) R" +
				" INNER JOIN (SELECT COUNT(ORDER_ID) AS ORDER_COUNT, STATUS FROM ORDERS WHERE TRADER_ID = ? GROUP BY STATUS) WHERE R.TRADER_ID = TRADER_ID";
		try {
			List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql, traderId, traderId);
			ActivitySummary summary = new ActivitySummary();
			Map<String,Long> orders = new HashMap<String, Long>();
			for (Map row : rows) {
				summary.setLastOrderPlacement((Timestamp)row.get("LATEST_ORDER"));
				orders.put(row.get("STATUS").toString(), (Long)row.get("ORDER_COUNT"));
			}
			summary.setOrders(orders);
			return summary;
		}catch(Exception ex) {
			throw new TraderNotFoundException("Invalid trader ID provided");
		}
	}
}
