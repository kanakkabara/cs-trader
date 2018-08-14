package com.cs.trader.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.cs.trader.domain.Company;
import com.cs.trader.domain.Sector;

@Repository
public class CompanyDao {
	@Autowired
	JdbcTemplate jdbc;
	
	public List<Company> findAllCompanies(){
		return jdbc.query("SELECT * FROM COMPANIES c, SECTORS s WHERE c.SECTOR_ID=s.SECTOR_ID", new CompanyRowMapper());
	}
	
	public List<Company> findAllCompaniesBySectorID(int id) {
		return jdbc.query("SELECT * FROM COMPANIES c, SECTORS s WHERE c.SECTOR_ID=s.SECTOR_ID AND c.SECTOR_ID = ?", new CompanyRowMapper(), id);
	}
	
	public Company findCompanyById(int id){
		return jdbc.queryForObject("SELECT * FROM COMPANIES c, SECTORS s WHERE c.SECTOR_ID=s.SECTOR_ID AND COMPANY_ID = ?", new CompanyRowMapper(), id);
	}

	class CompanyRowMapper implements RowMapper<Company> {
		@Override
		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
			Sector sec = new Sector(rs.getInt("SECTOR_ID"), rs.getString("SECTOR_NAME"), rs.getString("SECTOR_DESCRIPTION"));
			return new Company(rs.getInt("COMPANY_ID"), rs.getString("COMPANY_NAME"), rs.getString("TICKER_SYMBOL"), sec);
		}
	}
	
}
