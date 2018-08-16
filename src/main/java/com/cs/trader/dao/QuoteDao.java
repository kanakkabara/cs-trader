package com.cs.trader.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cs.trader.domain.Quote;
import com.cs.trader.exceptions.TraderNotFoundException;

@Repository
public class QuoteDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<Quote> findQuotes() {
		return jdbcTemplate.query("SELECT * FROM QUOTES ORDER BY FULFILLED_TIMESTAMP",
				new QuoteRowMapper());
	}
	
	public List<Quote> findQuotesByTicker(String ticker) {
		String sql = "SELECT QUOTES.*, ORDERS.SYMBOL FROM QUOTES, ORDERS, COMPANIES " + 
				"WHERE QUOTES.BUY_ID = ORDERS.ORDER_ID AND ORDERS.SYMBOL = COMPANIES.TICKER_SYMBOL " +
				"AND COMPANIES.TICKER_SYMBOL = ? " + 
				"ORDER BY FULFILLED_TIMESTAMP";
		try {
			return jdbcTemplate.query(sql,
				new QuoteRowMapper(), ticker);
		}catch(Exception ex) {
			throw new TraderNotFoundException("No quotes from ticker symbol " + ticker +
					"can be found.");
		}
	}
	
	public List<Quote> findQuotesByTickerAndTimestamp(String ticker, Date timestampFrom,
			Date timestampAfter) {
		String sql = "SELECT QUOTES.*, ORDERS.SYMBOL FROM QUOTES, ORDERS, COMPANIES " + 
				"WHERE QUOTES.BUY_ID = ORDERS.ORDER_ID AND ORDERS.SYMBOL = COMPANIES.TICKER_SYMBOL " + 
				"AND COMPANIES.TICKER_SYMBOL = ? AND FULFILLED_TIMESTAMP >= ? AND FULFILLED_TIMESTAMP <= ? " + 
				"ORDER BY FULFILLED_TIMESTAMP;";
		try {
			return jdbcTemplate.query(sql,
				new QuoteRowMapper(), ticker, new Timestamp(timestampFrom.getTime()), new Timestamp(timestampAfter.getTime()));
		}catch(Exception ex) {
			throw new TraderNotFoundException("No quotes from ticker symbol " + ticker +
					"can be found in period " + timestampFrom + " to " + timestampAfter);
		}
	}
	
	public List<Quote> findQuotesByTimestamp(Date timestampFrom, Date timestampAfter) {
		String sql = "SELECT QUOTES.*, ORDERS.SYMBOL FROM QUOTES, ORDERS, COMPANIES " + 
				"WHERE QUOTES.BUY_ID = ORDERS.ORDER_ID AND ORDERS.SYMBOL = COMPANIES.TICKER_SYMBOL " + 
				"AND FULFILLED_TIMESTAMP >= ? AND FULFILLED_TIMESTAMP <= ? " + 
				"ORDER BY FULFILLED_TIMESTAMP;";
		try {
			return jdbcTemplate.query(sql,
				new QuoteRowMapper(), new Timestamp(timestampFrom.getTime()), new Timestamp(timestampAfter.getTime()));
		}catch(Exception ex) {
			throw new TraderNotFoundException("No quotes from can be found in period specified.");
		}
	}
	
	class QuoteRowMapper implements RowMapper<Quote>{
		
		@Override
		public Quote mapRow(ResultSet rs, int rowNum) throws SQLException {
			Quote quote = new Quote();
			quote.setQuoteId(rs.getLong("QUOTE_ID"));
			quote.setBuyId(rs.getLong("BUY_ID"));
			quote.setSellId(rs.getLong("SELL_ID"));
			quote.setPrice(rs.getDouble("PRICE"));
			quote.setVolume(rs.getInt("VOLUME"));
			quote.setFulfilledTimestamp(rs.getTimestamp("FULFILLED_TIMESTAMP"));
			return quote;
		}
	}
	
}
