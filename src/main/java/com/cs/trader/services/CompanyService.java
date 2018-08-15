package com.cs.trader.services;

import com.cs.trader.dao.CompanyDao;
import com.cs.trader.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
	@Autowired
    private CompanyDao companyDao;
	
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

	public Company validateCompanyByTicker(String ticker){
		return companyDao.validateTicker(ticker);
	}

	public int addNewCompany(Company company) {
		return companyDao.addNewCompany(company);
	}

	public int deleteCompany(int companyID){
		return companyDao.deleteCompany(companyID);
	}

	public Company updateCompany(int id, Company company) {
		return companyDao.updateCompany(id, company);
	}
}
