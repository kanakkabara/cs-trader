package com.cs.trader.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trader.dao.SectorDao;
import com.cs.trader.domain.Company;
import com.cs.trader.domain.Sector;

@Service
public class SectorService {
	@Autowired
	SectorDao sectorDao; 
	
	public List<Sector> findAllSectors(){
		return sectorDao.findAllSectors();
	}
	
	public Sector findSectorByID(int id){
		return sectorDao.findSectorByID(id);
	}

	public int addNewSector(Sector sector) {
		return sectorDao.addNewSector(sector);
	}
	
	public int deleteSector(int sectorID) {
		return sectorDao.deleteSector(sectorID);
	}
	
	public Sector updateSector(int sectorID, Sector sector) {
		return sectorDao.updateSector(sectorID, sector);
	}
	
}
