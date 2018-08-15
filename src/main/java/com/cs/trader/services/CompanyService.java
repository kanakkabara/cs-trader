package com.cs.trader.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trader.dao.CompanyDao;
import com.cs.trader.domain.Company;
import com.cs.trader.domain.Sector;

@Service
public class CompanyService {
	@Autowired
	CompanyDao companyDao; 
	
	public List<Company> findAllCompanies(){
		return companyDao.findAllCompanies();
	}
	
	public List<Company> findAllCompaniesBySectorID(int id){
		return companyDao.findAllCompaniesBySectorID(id);
	}

//	public Company addNewCompany(Company company) {
//		return companyDao.save(company);
//	}
//	
}
