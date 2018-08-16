package com.cs.trader.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trader.dao.QuoteDao;
import com.cs.trader.domain.Quote;
import com.cs.trader.exceptions.InvalidFieldException;
import com.cs.trader.exceptions.TraderNotFoundException;

@Service
public class QuoteService {
	@Autowired
	QuoteDao dao;
	
	public List<Quote> findQuotes(String from, String to) {
		if(from == null || to == null) {
			return dao.findQuotes();
		}else {
			Timestamp timestampFrom = stringToTimestamp(from);
			Timestamp timestampAfter = stringToTimestamp(to);
			if(timestampFrom == null || timestampAfter == null) {
				throw new InvalidFieldException("Error in date format, make sure it's in yyyy-MM-dd hh:mm:ss.SSS");
			}
			return dao.findQuotesByTimestamp(timestampFrom, timestampAfter);
		}
	}
	
	public List<Quote> findQuotesByTicker(String ticker, String from, String to) {
		if(from == null || to == null) {
			return dao.findQuotesByTicker(ticker);
		}else {
			Timestamp timestampFrom = stringToTimestamp(from);
			Timestamp timestampAfter = stringToTimestamp(to);
			if(timestampFrom == null || timestampAfter == null) {
				throw new InvalidFieldException("Error in date format, make sure it's in yyyy-MM-dd hh:mm:ss.SSS");
			}
			return dao.findQuotesByTickerAndTimestamp(ticker, timestampFrom, timestampAfter);
		}
		
	}
	
	public Timestamp stringToTimestamp(String date) {
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    Date parsedDate = dateFormat.parse(date);
		    return new Timestamp(parsedDate.getTime());
		} catch(Exception e) { //this generic but you can control another types of exception
		    // look the origin of excption 
		}
		return null;
	}
}
