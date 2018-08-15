package com.cs.trader.dao;

import com.cs.trader.domain.Company;
import com.cs.trader.exceptions.CompanyHasExistingOrdersException;
import com.cs.trader.exceptions.CompanyNotFoundException;
import com.cs.trader.exceptions.SectorNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.cs.trader.CsTraderApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
@Transactional
public class CompanyDaoTest {
    @Autowired
    CompanyDao compDao;

    @Test
    public void findAllCompaniesReturnsAllCompanies() {
        assertTrue("Number of companies found is incorrect.", compDao.findAllCompanies().size() == 3);
    }

    @Test
    public void findAllCompaniesBySectorIDReturnsValidCompanies() {
        assertTrue("Number of companies found in Sector 1 is incorrect.", compDao.findAllCompaniesBySectorID(1).size() == 1);
        assertTrue("Number of companies found in Sector 2 is incorrect.", compDao.findAllCompaniesBySectorID(2).size() == 2);
    }

    @Test
    public void findAllCompaniesStartingWorksWithGeneralTicker() {
        List<Company> companies = compDao.findAllCompaniesStartingWith("COMP");
        assertTrue("Companies with ticker starting COMP returns incorrect number of companies", companies.size() == 3);
    }

    @Test
    public void findAllCompaniesStartingWorksWithSpecificTicker() {
        List<Company> companies = compDao.findAllCompaniesStartingWith("COMP1");
        assertTrue("Companies with ticker starting COMP1 returns incorrect number of company", companies.size() == 1);
        assertTrue("Companies with ticker starting COMP1 returns inappropriate company", companies.get(0).getTicker().equals("COMP1"));
    }

    @Test
    public void findAllCompaniesStartingFailsWithInvalidTicker() {
        List<Company> companies = compDao.findAllCompaniesStartingWith("COMP4");
        assertTrue("Companies with ticker starting COMP4 returns invalid companies", companies.size() == 0);
    }

    @Test
    public void findAllCompaniesStartingWorksWithGeneralName() {
        List<Company> companies = compDao.findAllCompaniesStartingWith("Company");
        assertTrue("Companies with name starting Company incorrect number of companies", companies.size() == 3);
    }

    @Test
    public void findAllCompaniesStartingWorksWithSpecificName() {
        List<Company> companies = compDao.findAllCompaniesStartingWith("Company 1");
        assertTrue("Companies with name starting Company 1 returns incorrect number of company", companies.size() == 1);
        assertTrue("Companies with name starting Company 1 returns the inappropriate company", companies.get(0).getCompanyName().equals("Company 1"));
    }

    @Test
    public void findAllCompaniesStartingFailsWithInvalidName() {
        List<Company> companies = compDao.findAllCompaniesStartingWith("COMP4");
        assertTrue("Companies with name starting Company returns invalid companies", companies.size() == 0);
    }

    @Test
    public void validateTickerWorksWithAValidTicker() {
        Company comp = compDao.validateTicker("COMP1");
        assertTrue("COMP1 is a valid ticker, but is returned as invalid", comp.getTicker().equals("COMP1"));
    }

    @Test(expected = CompanyNotFoundException.class)
    public void validateTickerFailsWithAnInvalidTicker() {
        compDao.validateTicker("COMP4");
    }

    @Test(expected = CompanyNotFoundException.class)
    public void findCompanyByIdFailsForInvalidID(){
        compDao.findCompanyByID(100);
    }

    @Test
    public void findCompanyByIdReturnsRightCompanyForValidID(){
        Company comp = compDao.findCompanyByID(1);
        assertTrue("1 is a valid ID, but can't find the company", comp.getCompanyID() == 1);
    }

    @Test(expected = SectorNotFoundException.class)
    public void addCompanyFailsForInvalidSector(){
        compDao.addNewCompany(new Company(0, "CompName", "CompTicker", 4));
    }

    @Test
    public void addCompanySucceedsNormally(){
        //TODO mock sector service
        int initSize = compDao.findAllCompanies().size();
        compDao.addNewCompany(new Company(0, "CompName", "CompTicker", 2));
        assertEquals("New company was not added successfully", initSize+1, compDao.findAllCompanies().size());
    }

    @Test
    public void addCompanyReturnsIdOfNewObject(){
        //TODO mock sector service
        int cID = compDao.addNewCompany(new Company(0, "CompName", "CompTicker", 2));
        assertEquals("ID of new company is not returned", 5, cID);
    }

    @Test(expected = CompanyHasExistingOrdersException.class)
    public void deleteFailsIfOrdersExist(){
        compDao.deleteCompany(1);
    }

    @Test
    public void deleteSucceedsIfNoOrders(){
        compDao.deleteCompany(2);
    }

    @Test(expected = CompanyNotFoundException.class)
    public void updateErrorIfInvalidID(){
        compDao.updateCompany(50, new Company());
    }

    @Test(expected = SectorNotFoundException.class)
    public void updateErrorIfNewSectorInvalid(){
        compDao.updateCompany(1, new Company(0, null, null, 5));
    }

    @Test
    public void updateWorksInCaseOfMissingFields(){
        assertEquals(1, compDao.findCompanyByID(1).getSectorID());
        compDao.updateCompany(1, new Company(0, null, null, 2));
        assertEquals(2, compDao.findCompanyByID(1).getSectorID());

        assertEquals("Company 1", compDao.findCompanyByID(1).getCompanyName());
        compDao.updateCompany(1, new Company(0, "Company xx", null, 0));
        assertEquals("Company xx", compDao.findCompanyByID(1).getCompanyName());

        assertEquals("COMP1", compDao.findCompanyByID(1).getTicker());
        compDao.updateCompany(1, new Company(0, null, "COMP5", 0));
        assertEquals("COMP5", compDao.findCompanyByID(1).getTicker());
    }
}
