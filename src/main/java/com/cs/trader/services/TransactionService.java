package com.cs.trader.services;

import com.cs.trader.dao.TransactionDao;
import com.cs.trader.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    TransactionDao transactionDao;

    public int addNewTransaction(Transaction transaction){
        return transactionDao.addNewTransaction(transaction);
    }
}
