package com.cs.trader.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cs.trader.domain.Trader;

public class TraderDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int addTrader(Trader trader) {
		String sql = "insert into TRADERS (firstName, lastName, email, phone, address) values(?,?,?,?,?)";
		int status = jdbcTemplate.update(sql,
				new Object[] { trader.getFirstName(), trader.getLastName(), trader.getEmail(), 
						trader.getPhone(), trader.getAddress()});
		return status;
	}
	
	public int deleteTrader(int id) {
		return -1;
	}
	
	public List<Trader> findTraders(){
		return null;
	}
	
	public Trader findTraderById(int id) {
		return null;
	}
	
	
}
