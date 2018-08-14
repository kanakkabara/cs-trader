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
import com.cs.trader.exceptions.InvalidFieldException;
import com.cs.trader.exceptions.TraderNotFoundException;

@Repository
public class TraderDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int addTrader(Trader trader) {
		String sql = "INSERT INTO TRADERS (FIRST_NAME, LAST_NAME, EMAIL, PHONE, ADDRESS) VALUES(?,?,?,?,?)";
		try {
			int status = jdbcTemplate.update(sql,
					new Object[] { trader.getFirstName(), trader.getLastName(), trader.getEmail(), 
							trader.getPhone(), trader.getAddress()});
			return status;
		}catch(DataIntegrityViolationException ex) {
			throw new InvalidFieldException("Invalid fields, please check again.");
		}
	}
	
	public int deleteTrader(long id) {
		String sql = "DELETE FROM TRADERS WHERE TRADER_ID = ?";
		int status = jdbcTemplate.update(sql, new Object[] {id});
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
	
	class TraderRowMapper implements RowMapper<Trader>{
		@Override
		public Trader mapRow(ResultSet rs, int rowNum) throws SQLException {
			Trader trader = new Trader();
			trader.setTraderId(rs.getInt("TRADER_ID"));
			trader.setFirstName(rs.getString("FIRST_NAME"));
			trader.setLastName(rs.getString("LAST_NAME"));
			trader.setEmail(rs.getString("EMAIL"));
			trader.setPhone(rs.getString("PHONE"));
			trader.setAddress(rs.getString("ADDRESS"));
			return trader;
		}
	}
	
	
}
