package com.cs.trader.services;

import java.util.List;

import com.cs.trader.exceptions.SectorHasValidCompanies;
import com.cs.trader.exceptions.SectorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trader.dao.SectorDao;
import com.cs.trader.domain.Company;
import com.cs.trader.domain.Sector;

@Service
public class SectorService {
	@Autowired
	private SectorDao sectorDao;

	@Autowired
	private CompanyService companyService;
	
	public List<Sector> findAllSectors(){
		return sectorDao.findAllSectors();
	}
	
	public Sector findSectorByID(int id){
		return new Sector(sectorDao.findSectorByID(id), companyService.findAllCompaniesBySectorID(id));
	}

	public int addNewSector(Sector sector) {
		return sectorDao.addNewSector(sector);
	}
	
	public int deleteSector(int sectorID) {
		int numberOfCompanies = companyService.findAllCompaniesBySectorID(sectorID).size();
		if(numberOfCompanies == 0) {
			return sectorDao.deleteSector(sectorID);
		}else {
			throw new SectorHasValidCompanies("Sector ID["+sectorID+"] has some valid companies, cannot perform delete operation!");
		}
	}
	
	public Sector updateSector(int sectorID, Sector sector) {
		try {
			findSectorByID(sectorID);
		}catch(Exception e) {
			throw new SectorNotFoundException("Could not find a sector with Sector ID["+sectorID+"]");
		}

		sector.setSectorID(sectorID);
		return sectorDao.updateSector(sector);
	}
}
