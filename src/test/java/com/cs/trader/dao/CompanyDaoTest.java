package com.cs.trader.dao;

import com.cs.trader.domain.Company;
import com.cs.trader.exceptions.CompanyHasExistingOrdersException;
import com.cs.trader.exceptions.CompanyNotFoundException;
import com.cs.trader.exceptions.SectorNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cs.trader.CsTraderApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
@Transactional
public class CompanyDaoTest {
    @Autowired
    private CompanyDao compDao;

    @Test
    public void findAllCompaniesReturnsAllCompanies() {
        assertTrue("Number of companies found is incorrect.", compDao.findAllCompanies().size() == 4);
    }

    @Test
    public void findAllCompaniesBySectorIDReturnsValidCompanies() {
        assertTrue("Number of companies found in Sector 1 is incorrect.", compDao.findAllCompaniesBySectorID(1).size() == 1);
        assertTrue("Number of companies found in Sector 2 is incorrect.", compDao.findAllCompaniesBySectorID(2).size() == 3);
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
        assertEquals("ID of new company is not returned", 6, cID);
    }

    @Test
    public void deleteSucceedsIfNoOrders(){
        compDao.deleteCompany(2);
    }
}
