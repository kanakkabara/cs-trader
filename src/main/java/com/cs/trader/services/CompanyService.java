package com.cs.trader.services;

import com.cs.trader.dao.CompanyDao;
import com.cs.trader.domain.Company;
import com.cs.trader.domain.Order;
import com.cs.trader.exceptions.CompanyHasExistingOrdersException;
import com.cs.trader.exceptions.DuplicateTickerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
	@Autowired
    private CompanyDao companyDao;

	@Autowired
	private SectorService sectorService;

	@Autowired
	private OrderService orderService;
	
	public List<Company> findAllCompanies(){
		return companyDao.findAllCompanies();
	}

	public Company findCompanyByID(int id) {
		return companyDao.findCompanyByID(id);
	}

	public List<Company> findAllCompaniesStartingWith(String startsWith) {
		return companyDao.findAllCompaniesStartingWith(startsWith);
	}

	public List<Company> findAllCompaniesBySectorID(int id){
		return companyDao.findAllCompaniesBySectorID(id);
	}

	Company validateCompanyByTicker(String ticker){
		return companyDao.validateTicker(ticker);
	}

	public int addNewCompany(Company company) {
		sectorService.findSectorByID(company.getSectorID());
		try {
			validateCompanyByTicker(company.getTicker());
		}catch(Exception e){
			return companyDao.addNewCompany(company);
		}
		throw new DuplicateTickerException("Company with ticker = "+company.getTicker()+" already exists!");
	}

	public int deleteCompany(int companyID){
		int ordersForCompany = orderService.retrieveOrdersByCompany(findCompanyByID(companyID)).size();
		if(ordersForCompany == 0) {
			return companyDao.deleteCompany(companyID);
		}else {
			throw new CompanyHasExistingOrdersException("Company ID["+companyID+"] has some valid orders, cannot perform delete operation!");
		}
	}

	public Company updateCompany(int id, Company newComp) {
		Company oldComp = findCompanyByID(id);

		String ticker = newComp.getTicker() == null ? oldComp.getTicker() : newComp.getTicker();
		String companyName = newComp.getCompanyName() == null ? oldComp.getCompanyName() : newComp.getCompanyName();
		int sectorID = newComp.getSectorID() == 0 ? oldComp.getSectorID() : sectorService.findSectorByID(newComp.getSectorID()).getSectorID();

		return companyDao.updateCompany(new Company(id, companyName, ticker, sectorID));
	}
}
