package com.cs.trader.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.trader.dao.TraderDao;
import com.cs.trader.domain.Trader;

@Service
public class TraderService {
	
	@Autowired
	TraderDao dao;
	
	public int addTrader(Trader trader) {
		int status = dao.addTrader(trader);
		return status;
		
	}
	
	public int deleteTrader(long id) {
		//validate if trader has existing orders
		int status = -1;
		//if(!hasExistingOrders){
			status = dao.deleteTrader(id);
		//}
		return status;
	}
	
	public List<Trader> findTraders(){
		return dao.findTraders();
	}
	
	public Trader findTraderById(long id) {
		return dao.findTraderById(id);
	}
	
}
