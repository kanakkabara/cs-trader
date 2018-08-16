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

import com.cs.trader.domain.Sector;
import com.cs.trader.exceptions.SectorNotFoundException;
import com.cs.trader.exceptions.SectorHasValidCompanies;
import com.cs.trader.services.CompanyService;

@Repository
public class SectorDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	public List<Sector> findAllSectors(){
		return jdbc.query("SELECT s.SECTOR_ID, SECTOR_NAME, SECTOR_DESCRIPTION, COUNT(c.COMPANY_ID) as COMPANY_COUNT FROM SECTORS s LEFT JOIN COMPANIES c ON s.SECTOR_ID = c.SECTOR_ID GROUP BY s.SECTOR_ID", new SectorRowMapper());
	}
	
	public Sector findSectorByID(int id){
		try {
			return jdbc.queryForObject("select * from sectors where SECTOR_ID = ?", new SectorRowMapper(), id);
		}catch(Exception e) {
			throw new SectorNotFoundException("Could not find a sector with Sector ID["+id+"]");
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
	
	public Sector updateSector(Sector sector){
		int rows = jdbc.update(new PreparedStatementCreator() {
		    @Override
		    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		        PreparedStatement statement = con.prepareStatement("UPDATE SECTORS SET SECTOR_DESCRIPTION=?, SECTOR_NAME=? WHERE SECTOR_ID=?", Statement.RETURN_GENERATED_KEYS);
		        statement.setString(1, sector.getSectorDesc());
				statement.setString(2, sector.getSectorName());
		        statement.setInt(3, sector.getSectorID());
		        return statement;
		    }
		});
		
		if(rows != 0) return findSectorByID(sector.getSectorID());
		else throw new RuntimeException("Internal Server Error");

	}
	
	public int deleteSector(int sectorID){
		return jdbc.update("DELETE FROM SECTORS WHERE SECTOR_ID = ?", new Object[] {sectorID});
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
