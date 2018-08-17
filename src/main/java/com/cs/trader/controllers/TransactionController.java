package com.cs.trader.controllers;

import com.cs.trader.domain.Transaction;
import com.cs.trader.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/log")
    public List<Transaction> getAllTransactions(
            @RequestParam(value = "filter", required = false) String filters,
            @RequestParam(value = "ticker", required = false) String ticker,
            @RequestParam(value = "userID", required = false) String userID,
            @RequestParam(value = "startDateTime", required = false) String startDateTime,
            @RequestParam(value = "endDateTime", required = false) String endDateTime
    ){
        return transactionService.getTransactions(filters, ticker, startDateTime, endDateTime, userID);
    }
}
