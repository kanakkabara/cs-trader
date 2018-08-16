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
//    @Autowired
//    TransactionService transactionService;
//
//    @GetMapping("/log")
//    public List<Transaction> getAllTransactions(
//            @RequestParam(value = "startsWith", required = false) String startsWith
//    ){
//        return transactionService.getTransactions();
//    }
}
