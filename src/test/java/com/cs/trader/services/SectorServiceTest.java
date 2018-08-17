package com.cs.trader.services;


import com.cs.trader.CsTraderApplication;
import com.cs.trader.dao.SectorDao;
import com.cs.trader.domain.Company;
import com.cs.trader.domain.Sector;
import com.cs.trader.exceptions.SectorHasValidCompanies;
import com.cs.trader.exceptions.SectorNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
@Transactional
public class SectorServiceTest {
    @InjectMocks
    @Resource
    private SectorService sectorService;

    @Mock
    private SectorDao sectorDao;

    @Mock
    private CompanyService companyService;

    @Before
    public void setUp() throws Exception {
        // Initialize mocks created above
        MockitoAnnotations.initMocks(this);

        Company company1 = new Company(1, "Company 1", "COMP1", 1);
        Company company2 = new Company(2, "Company 2", "GOOGL", 2);
        Company company3 = new Company(3, "Company 3", "AAPL", 2);
        Company company4 = new Company(4, "Company 4", "IHSG", 2);
        Mockito.when(companyService.findAllCompaniesBySectorID(1)).thenReturn(new ArrayList<>(Arrays.asList(company1)));
        Mockito.when(companyService.findAllCompaniesBySectorID(3)).thenReturn(new ArrayList<>());

        Sector sector1 = new Sector(1, "Sector1", "Desc for Sector 1");
        Sector sector2 = new Sector(2, "Sector2", "Desc for Sector 2");
        Mockito.when(sectorDao.findSectorByID(50)).thenThrow(new SectorNotFoundException("test"));
        Mockito.when(sectorDao.findSectorByID(1)).thenReturn(sector1);
        Mockito.when(sectorDao.findSectorByID(2)).thenReturn(sector2);
        Mockito.when(companyService.findAllCompaniesBySectorID(1)).thenReturn(new ArrayList<>(Arrays.asList(company1)));
        Mockito.when(companyService.findAllCompaniesBySectorID(2)).thenReturn(
                new ArrayList<>(Arrays.asList(company2, company3, company4)));


    }

    @Test(expected = SectorHasValidCompanies.class)
    public void deleteFailsIfSectorHasValidCompanies(){
        sectorService.deleteSector(1);
    }

    @Test
    public void deleteSucceedsIfSectorDoesHasValidCompanies(){
        sectorService.deleteSector(3);
        verify(sectorDao, times(1)).deleteSector(3);

    }

    @Test(expected = SectorNotFoundException.class)
    public void updateErrorIfInvalidSectorID(){
        sectorService.updateSector(50, new Sector());
    }

    @Test
    public void updateSucceedsOnValidSectorId(){
        sectorService.updateSector(1, new Sector());
        verify(sectorDao, times(1)).updateSector(any());
    }

    @Test
    public void findSectorByIDPopulatesWithCorrectCompanies(){
        Sector s = sectorService.findSectorByID(2);
        assertEquals(s.getCompanies().size(), 3);
        for(Company c : s.getCompanies())
            assertEquals(c.getSectorID(), 2);
    }
}
