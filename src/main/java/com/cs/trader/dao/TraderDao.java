package com.cs.trader.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cs.trader.domain.Trader;
import com.cs.trader.domain.TraderRank;
import com.cs.trader.exceptions.InvalidFieldException;
import com.cs.trader.exceptions.TraderNotFoundException;

@Repository
public class TraderDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int addTrader(Trader trader) {
		String sql = "INSERT INTO TRADERS (FIRST_NAME, LAST_NAME, EMAIL, PHONE, ADDRESS, USERNAME) VALUES(?,?,?,?,?,?)";
		try {
			int status = jdbcTemplate.update(sql,
					new Object[] { trader.getFirstName(), trader.getLastName(), trader.getEmail(), 
							trader.getPhone(), trader.getAddress(), trader.getUsername()});
			return status;
		}catch(DataIntegrityViolationException ex) {
			throw new InvalidFieldException("Invalid fields, please check again.");
		}
	}
	
	public int deleteTrader(long id) {
		String sql = "DELETE FROM TRADERS WHERE TRADER_ID = ?";
		int status = jdbcTemplate.update(sql, new Object[] {id});
		if(status == 0) {
			throw new TraderNotFoundException("Trader with id " + id + " can't be found.");
		}
		return status;
	}
	
	public List<Trader> findTraders(){
		return jdbcTemplate.query("SELECT * FROM TRADERS",
				new TraderRowMapper());
	}
	
	public Trader findTraderById(long id) {
		String sql = "SELECT * FROM TRADERS WHERE TRADER_ID = ?";
		try {
			return jdbcTemplate.queryForObject(sql,
				new TraderRowMapper(), id);
		}catch(Exception ex) {
			throw new TraderNotFoundException("Trader with id " + id + " can't be found.");
		}
	}
	
	public Trader findTraderByUsername(String username) {
		String sql = "SELECT * FROM TRADERS WHERE USERNAME = ?";
		try {
			return jdbcTemplate.queryForObject(sql,
				new TraderRowMapper(), username);
		}catch(Exception ex) {
			throw new TraderNotFoundException("Trader with id " + username + " can't be found.");
		}
	}
	
	public List<TraderRank> findTopFiveTradersByNumTrades() {
		String sql = "SELECT TOP 5 COUNT(ORDER_ID) NUM_ORDERS, TRADERS.* " + 
				"FROM TRADERS " + 
				"LEFT OUTER JOIN ORDERS ON TRADERS.TRADER_ID = ORDERS.TRADER_ID " + 
				"GROUP BY TRADERS.TRADER_ID " + 
				"ORDER BY NUM_ORDERS DESC";
		return jdbcTemplate.query(sql,
				new TraderRankRowMapperNumTrades());
	}
	
	public List<TraderRank> findTopFiveTradersByVolume() {
		String sql = "SELECT TOP 5 ISNULL(SUM(VOLUME), 0) VOLUME, TRADERS.* " + 
				"FROM TRADERS " + 
				"LEFT OUTER JOIN ORDERS ON TRADERS.TRADER_ID = ORDERS.TRADER_ID " + 
				"GROUP BY TRADERS.TRADER_ID " + 
				"ORDER BY VOLUME DESC";
		return jdbcTemplate.query(sql,
				new TraderRankRowMapperVolume());
	}
	
	
	class TraderRowMapper implements RowMapper<Trader>{
		@Override
		public Trader mapRow(ResultSet rs, int rowNum) throws SQLException {
			Trader trader = TraderDao.mapRow(rs, rowNum);
			return trader;
		}
	}
	
	class TraderRankRowMapperVolume implements RowMapper<TraderRank>{
		@Override
		public TraderRank mapRow(ResultSet rs, int rowNum) throws SQLException {
			Trader trader = TraderDao.mapRow(rs, rowNum);
			TraderRank traderRank = new TraderRank(trader, rs.getLong("VOLUME"));
			return traderRank;
		}
	}
	
	class TraderRankRowMapperNumTrades implements RowMapper<TraderRank>{
		@Override
		public TraderRank mapRow(ResultSet rs, int rowNum) throws SQLException {
			Trader trader = TraderDao.mapRow(rs, rowNum);
			TraderRank traderRank = new TraderRank(trader, rs.getLong("NUM_ORDERS"));
			return traderRank;
		}
	}
	
	private static Trader mapRow(ResultSet rs, int rowNum) throws SQLException {
		Trader trader = new Trader();
		trader.setTraderId(rs.getLong("TRADER_ID"));
		trader.setFirstName(rs.getString("FIRST_NAME"));
		trader.setLastName(rs.getString("LAST_NAME"));
		trader.setEmail(rs.getString("EMAIL"));
		trader.setPhone(rs.getString("PHONE"));
		trader.setAddress(rs.getString("ADDRESS"));
		trader.setUsername(rs.getString("USERNAME"));
		return trader;
	}
	
	
	
}
