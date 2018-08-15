package com.cs.trader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.trader.domain.Company;
import com.cs.trader.services.CompanyService;

@RestController
public class CompanyController {
	@Autowired
	CompanyService companyService;
	
	@GetMapping("/companies")
	public List<Company> findAllSectors(){
		return companyService.findAllCompanies();
	}
}
