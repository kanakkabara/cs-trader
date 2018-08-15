package com.cs.trader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cs.trader.domain.Company;
import com.cs.trader.services.CompanyService;

@RestController
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	@GetMapping("/companies")
	public List<Company> findAllCompanies(@RequestParam(value = "startsWith", required = false) String startsWith){
		if(startsWith == null) return companyService.findAllCompanies();
		return companyService.findAllCompaniesStartingWith(startsWith);
	}

	@GetMapping("/companies/{id}")
	public Company findCompaniesById(@PathVariable int id){
		return companyService.findCompanyByID(id);
	}

	@PostMapping("/companies")
	public int addNewCompany(@RequestBody Company company){
		return companyService.addNewCompany(company);
	}

	@DeleteMapping("/companies/{id}")
	public void deleteCompany(@PathVariable int id){
		companyService.deleteCompany(id);
	}

	@PutMapping("/companies/{id}")
	public Company updateCompany(@PathVariable int id, @RequestBody Company company){
		return companyService.updateCompany(id, company);
	}
}
