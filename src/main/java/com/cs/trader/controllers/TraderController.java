package com.cs.trader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cs.trader.domain.Trader;
import com.cs.trader.services.TraderService;

@RestController
public class TraderController {
	
	@Autowired
	TraderService service;
	
	@GetMapping(value="/traders", produces= {MediaType.APPLICATION_JSON_VALUE})
	public List<Trader> findTraders(){
		return service.findTraders();
	}
	
	@GetMapping(value="/traders/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Trader findTraderById(@PathVariable(value="id") long id) {
		return service.findTraderById(id);
	}
	
	@PostMapping(value="/traders", produces= {MediaType.APPLICATION_JSON_VALUE})
	public int addTrader(@RequestBody Trader trader) {
		return service.addTrader(trader);
	}
	
	@DeleteMapping(value="/traders/{id}", produces= {MediaType.APPLICATION_JSON_VALUE})
	public int deleteTrader(@PathVariable(value="id") long id) {
		return service.deleteTrader(id);
	}
}
