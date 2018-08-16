package com.cs.trader.dao;

import com.cs.trader.CsTraderApplication;
import com.cs.trader.domain.Sector;
import com.cs.trader.exceptions.SectorNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
@Transactional
public class SectorDaoTest {
    @Autowired
    SectorDao sectorDao;

    @Test
    public void findAllSectorsReturnsAllSectors() {
        assertEquals("Number of sectors found is incorrect.", 3, sectorDao.findAllSectors().size());
    }

    @Test
    public void findAllSectorsReturnsCompanyCountForEachSector() {
        List<Sector> sectors = sectorDao.findAllSectors();
        assertEquals("Sector 1 has 1 company", 1, sectors.get(0).getCompanyCount());
        assertEquals("Sector 2 has 2 companies", 3, sectors.get(1).getCompanyCount());
        assertEquals("Sector 3 has 0 companies", 0, sectors.get(2).getCompanyCount());
    }

    @Test(expected = SectorNotFoundException.class)
    public void findSectorByIdFailsForInvalidID(){
        sectorDao.findSectorByID(100);
    }

    @Test
    public void findCompanyByIdReturnsRightCompanyForValidID(){
        Sector sector = sectorDao.findSectorByID(1);
        assertEquals("1 is a valid ID, but can't find the sector", 1, sector.getSectorID());
    }

    @Test
    public void addSectorSucceedsNormallyAndReturnsNewID(){
        int initSize = sectorDao.findAllSectors().size();
        int id = sectorDao.addNewSector(new Sector(0,"SecName", "SecDesc"));
        assertEquals("New sector was not added successfully", initSize+1, sectorDao.findAllSectors().size());
        assertEquals("ID of new sector is not returned", 4, id);

    }

    @Test
    public void deleteSucceedsIfNoCompanies(){
        sectorDao.deleteSector(3);
    }
}