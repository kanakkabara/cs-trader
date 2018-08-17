package com.cs.trader.services;

import com.cs.trader.dao.TransactionDao;
import com.cs.trader.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    TransactionDao transactionDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TraderService traderService;

    public List<Transaction> getTransactions(String filter, String ticker, String startDateTime, String endDateTime, String userID){
        List<Transaction> transactions = transactionDao.getAllTransactions();

        transactions = transactions.stream().filter(
            transaction -> {
                boolean result = true;
                if(ticker != null)
                    result = orderService.retrieveOrderByOrderId(transaction.getOrderID()).getSymbol().equals(ticker);
                if(userID != null)
                    result = result && (Long.toString(transaction.getTraderID()).equals(userID));
                if(endDateTime != null)
                    result = result && transaction.getTransactionTimestamp().before(Timestamp.valueOf(endDateTime));
                if(startDateTime != null)
                    result = result && transaction.getTransactionTimestamp().after(Timestamp.valueOf(startDateTime));

                System.out.println(transaction.getTransactionTimestamp());

                return result;
            }
        ).collect(Collectors.toList());

        if(filter != null){
            if(!filter.contains("transactionID"))
                transactions.forEach(transaction -> transaction.setTransactionID(null));
            if(!filter.contains("transactionType"))
                transactions.forEach(transaction -> transaction.setTransactionType(null));
            if(!filter.contains("orderID"))
                transactions.forEach(transaction -> transaction.setOrderID(null));
            if(!filter.contains("traderID"))
                transactions.forEach(transaction -> transaction.setTraderID(null));
            if(!filter.contains("orderSide"))
                transactions.forEach(transaction -> transaction.setOrderSide(null));
            if(!filter.contains("price"))
                transactions.forEach(transaction -> transaction.setPrice(null));
            if(!filter.contains("volume"))
                transactions.forEach(transaction -> transaction.setVolume(null));
            if(!filter.contains("transactionTimestamp"))
                transactions.forEach(transaction -> transaction.setTransactionTimestamp(null));
            if(!filter.contains("orderStatus"))
                transactions.forEach(transaction -> transaction.setOrderStatus(null));
            if(!filter.contains("orderType"))
                transactions.forEach(transaction -> transaction.setOrderType(null));
        }

        return transactions;
    }

    public int addNewTransaction(Transaction transaction){
        orderService.retrieveOrderByOrderId(transaction.getOrderID());
        traderService.findTraderById(transaction.getTraderID());

        return transactionDao.addNewTransaction(transaction);
    }
}
