package com.cs.trader.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.cs.trader.exceptions.CompanyHasExistingOrdersException;
import com.cs.trader.exceptions.CompanyNotFoundException;
import com.cs.trader.services.OrderService;
import com.cs.trader.services.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.cs.trader.domain.Company;
import com.cs.trader.domain.Sector;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CompanyDao {
	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private SectorService sectorService;

	@Autowired
	private OrderService orderService;
	
	public List<Company> findAllCompanies(){
		return jdbc.query("SELECT * FROM COMPANIES c, SECTORS s WHERE c.SECTOR_ID=s.SECTOR_ID", new CompanyRowMapper());
	}
	
	public List<Company> findAllCompaniesBySectorID(int id) {
		return jdbc.query("SELECT * FROM COMPANIES c, SECTORS s WHERE c.SECTOR_ID=s.SECTOR_ID AND c.SECTOR_ID = ?", new CompanyRowMapper(), id);
	}

	public List<Company> findAllCompaniesStartingWith(String startsWith) {
		startsWith += "%";
		return jdbc.query("SELECT * FROM COMPANIES c, SECTORS s WHERE c.SECTOR_ID=s.SECTOR_ID AND (c.COMPANY_NAME LIKE ? OR c.TICKER_SYMBOL LIKE ?)", new CompanyRowMapper(), startsWith, startsWith);
	}

	public Company validateTicker(String tickerToValidate) {
		try {
			return jdbc.queryForObject("SELECT * FROM COMPANIES c WHERE c.TICKER_SYMBOL LIKE ?", new CompanyRowMapper(), tickerToValidate);
		} catch(DataAccessException e){
			throw new CompanyNotFoundException("No company could be found with ticker symbol = "+tickerToValidate);
		}
	}

	public Company findCompanyByID(int id){
		try {
			return jdbc.queryForObject("SELECT * FROM COMPANIES c, SECTORS s WHERE c.SECTOR_ID=s.SECTOR_ID AND COMPANY_ID = ?", new CompanyRowMapper(), id);
		} catch(DataAccessException e){
			throw new CompanyNotFoundException("No company could be found with company ID = "+id);
		}
	}

	@Transactional
	public int addNewCompany(Company company) {
		sectorService.findSectorByID(company.getSectorID());

		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbc.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement("INSERT INTO COMPANIES(COMPANY_NAME, TICKER_SYMBOL, SECTOR_ID) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, company.getCompanyName());
				statement.setString(2, company.getTicker());
				statement.setInt(3, company.getSectorID());
				return statement;
			}
		}, holder);

		return holder.getKey().intValue();
	}

	@Transactional
	public int deleteCompany(int companyID){
		int ordersForCompany = orderService.retrieveOrdersByCompany(findCompanyByID(companyID)).size();
		if(ordersForCompany == 0) {
			return jdbc.update("DELETE FROM COMPANIES WHERE COMPANY_ID = ?", new Object[] {companyID});
		}else {
			throw new CompanyHasExistingOrdersException("Company ID["+companyID+"] has some valid orders, cannot perform delete operation!");
		}
	}

	@Transactional
	public Company updateCompany(int companyID, Company newCompany){
		Company oldComp = findCompanyByID(companyID);

		int newSectorID = newCompany.getSectorID() == 0 ? oldComp.getSectorID() : sectorService.findSectorByID(newCompany.getSectorID()).getSectorID();

		int rows = jdbc.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement("UPDATE COMPANIES SET TICKER_SYMBOL=?, COMPANY_NAME=?, SECTOR_ID=? WHERE COMPANY_ID=?; \n", Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, newCompany.getTicker() == null ? oldComp.getTicker() : newCompany.getTicker());
				statement.setString(2, newCompany.getCompanyName() == null ? oldComp.getCompanyName() : newCompany.getCompanyName());
				statement.setInt(3, newSectorID);
				statement.setInt(4, companyID);
				return statement;
			}
		});

		if(rows != 0) return findCompanyByID(companyID);
		else throw new RuntimeException("Internal Server Error");
	}

	class CompanyRowMapper implements RowMapper<Company> {
		@Override
		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Company(rs.getInt("COMPANY_ID"), rs.getString("COMPANY_NAME"), rs.getString("TICKER_SYMBOL"), rs.getInt("SECTOR_ID"));
		}
	}
	
}
