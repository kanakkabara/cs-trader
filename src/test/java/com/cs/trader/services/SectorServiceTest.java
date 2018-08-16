package com.cs.trader.services;


import com.cs.trader.CsTraderApplication;
import com.cs.trader.domain.Company;
import com.cs.trader.domain.Sector;
import com.cs.trader.exceptions.SectorHasValidCompanies;
import com.cs.trader.exceptions.SectorNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
@Transactional
public class SectorServiceTest {
    @Autowired
    SectorService sectorService;

    @Test(expected = SectorHasValidCompanies.class)
    public void deleteFailsIfSectorHasValidCompanies(){
        sectorService.deleteSector(1);
    }

    @Test(expected = SectorNotFoundException.class)
    public void updateErrorIfInvalidSectorID(){
        sectorService.updateSector(50, new Sector());
    }

    @Test
    public void findSectorByIDPopulatesWithCorrectCompanies(){
        Sector s = sectorService.findSectorByID(2);
        assertEquals(s.getCompanies().size(), 2);
        for(Company c : s.getCompanies())
            assertEquals(c.getSectorID(), 2);
    }
}
