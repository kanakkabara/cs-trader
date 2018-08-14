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
import com.cs.trader.exceptions.RecordNotFoundException;
import com.cs.trader.exceptions.SectorHasValidCompanies;
import com.cs.trader.services.CompanyService;

@Repository
public class SectorDao {
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	CompanyService companyService;
	
	public List<Sector> findAllSectors(){
		return jdbc.query("SELECT s.SECTOR_ID, SECTOR_NAME, SECTOR_DESCRIPTION, COUNT(*) as COMPANY_COUNT FROM SECTORS s, COMPANIES c WHERE s.SECTOR_ID = c.SECTOR_ID GROUP BY s.SECTOR_ID", new SectorRowMapper());
	}
	
	public Sector findSectorByID(int id){
		try {
			Sector s = jdbc.queryForObject("select * from sectors where SECTOR_ID = ?", new SectorRowMapper(), id);
			return new Sector(s, companyService.findAllCompaniesBySectorID(id));
		}catch(Exception e) {
			throw new RecordNotFoundException("Could not find a sector with Sector ID["+id+"]");
		}
	}
	
	public int addNewSector(Sector sec){
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbc.update(new PreparedStatementCreator() {
		    @Override
		    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		        PreparedStatement statement = con.prepareStatement("INSERT INTO SECTORS(SECTOR_NAME, SECTOR_DESCRIPTION) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
		        statement.setString(1, sec.getSectorName());
		        statement.setString(2, sec.getSectorDesc());
		        return statement;
		    }
		}, holder);
		
		return holder.getKey().intValue();
	}
	
	public Sector updateSector(int sectorID, Sector sector){
		try {
			findSectorByID(sectorID);
		}catch(Exception e) {
			throw new RecordNotFoundException("Could not find a sector with Sector ID["+sectorID+"]");
		}
		
		int rows = jdbc.update(new PreparedStatementCreator() {
		    @Override
		    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		        PreparedStatement statement = con.prepareStatement("UPDATE SECTORS SET SECTOR_DESCRIPTION=? WHERE SECTOR_ID=?", Statement.RETURN_GENERATED_KEYS);
		        statement.setString(1, sector.getSectorDesc());
		        statement.setInt(2, sectorID);
		        return statement;
		    }
		});
		
		if(rows != 0) return findSectorByID(sectorID);
		else{
			throw new RuntimeException("Internal Server Error");
		}
	}
	
	public int deleteSector(int sectorID){
		int numberOfCompanies = companyService.findAllCompaniesBySectorID(sectorID).size();
		if(numberOfCompanies == 0) {
			String sql = "DELETE FROM SECTORS WHERE SECTOR_ID = ?";
			int status = jdbc.update(sql, new Object[] {sectorID});
			return status;
		}else {
			throw new SectorHasValidCompanies("Sector ID["+sectorID+"] has some valid companies, cannot perform delete operation!");
		}
	}
	
	class SectorRowMapper implements RowMapper<Sector> {
		@Override
		public Sector mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				return new Sector(rs.getInt("SECTOR_ID"), rs.getString("SECTOR_NAME"), rs.getString("SECTOR_DESCRIPTION"), rs.getInt("COMPANY_COUNT"));
			} catch(Exception e) {
				return new Sector(rs.getInt("SECTOR_ID"), rs.getString("SECTOR_NAME"), rs.getString("SECTOR_DESCRIPTION"));
			}
		}
	}
}
