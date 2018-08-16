package com.cs.trader.services;

import com.cs.trader.CsTraderApplication;
import com.cs.trader.dao.CompanyDao;
import com.cs.trader.domain.Company;
import com.cs.trader.exceptions.CompanyHasExistingOrdersException;
import com.cs.trader.exceptions.CompanyNotFoundException;
import com.cs.trader.exceptions.DuplicateTickerException;
import com.cs.trader.exceptions.SectorNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
@Transactional
public class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;

    @Test(expected = CompanyHasExistingOrdersException.class)
    public void deleteFailsIfOrdersExist(){
        companyService.deleteCompany(1);
    }

    @Test(expected = CompanyNotFoundException.class)
    public void updateErrorIfInvalidID(){
        companyService.updateCompany(50, new Company(50, null, null, 1));
    }

    @Test(expected = SectorNotFoundException.class)
    public void updateErrorIfNewSectorInvalid(){
        companyService.updateCompany(2, new Company(1, null, null, 5));
    }

    @Test(expected = SectorNotFoundException.class)
    public void addCompanyFailsForInvalidSector(){
        companyService.addNewCompany(new Company(0, "CompName", "CompTicker", 4));
    }

    @Test(expected = DuplicateTickerException.class)
    public void addCompanyFailsForDuplicateTicker(){
        companyService.addNewCompany(new Company(0, "CompName", "COMP1", 2));
    }

    @Test
    public void updateWorksInCaseOfMissingFields(){
        assertEquals(2, companyService.findCompanyByID(2).getSectorID());
        companyService.updateCompany(2, new Company(0, null, null, 1));
        assertEquals(1, companyService.findCompanyByID(2).getSectorID());

        assertEquals("Company 2", companyService.findCompanyByID(2).getCompanyName());
        companyService.updateCompany(2, new Company(0, "Company xx", null, 0));
        assertEquals("Company xx", companyService.findCompanyByID(2).getCompanyName());

        assertEquals("COMP2", companyService.findCompanyByID(2).getTicker());
        companyService.updateCompany(2, new Company(0, null, "COMP5", 0));
        assertEquals("COMP5", companyService.findCompanyByID(2).getTicker());
    }
}
