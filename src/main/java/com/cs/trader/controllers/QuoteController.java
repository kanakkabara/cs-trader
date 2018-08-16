package com.cs.trader.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cs.trader.domain.Quote;
import com.cs.trader.services.QuoteService;

@RestController
public class QuoteController {
	
	@Autowired
	QuoteService service;
	
	@GetMapping(value="/quotes", produces= {MediaType.APPLICATION_JSON_VALUE})
	public List<Quote> addTrader(@RequestParam(value="from", required = false) String from, 
			@RequestParam(value="to", required = false) String to) {
		System.out.println(from);
		return service.findQuotes(from, to);
	}
	
	@GetMapping(value="/quotes/{tickersymbol}", produces= {MediaType.APPLICATION_JSON_VALUE})
	public List<Quote> deleteTrader(@PathVariable(value="tickersymbol") String ticker, 
			@RequestParam(value="from", required = false) String from, @RequestParam(value="to", 
			required = false) String to) {
		System.out.println("wew");
		System.out.println(ticker);
		System.out.println(from);
		return service.findQuotesByTicker(ticker, from, to);
	}
}
