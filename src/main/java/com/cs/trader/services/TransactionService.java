package com.cs.trader.services;

import com.cs.trader.dao.TransactionDao;
import com.cs.trader.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionDao transactionDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TraderService traderService;

    public List<Transaction> getTransactions(
        @RequestParam(value = "startsWith", required = false) String startsWith
    ){
        return transactionDao.getAllTransactions();
    }

    public int addNewTransaction(Transaction transaction){
        orderService.retrieveOrderByOrderId(transaction.getOrderID());
        traderService.findTraderById(transaction.getTraderID());

        return transactionDao.addNewTransaction(transaction);
    }
}
