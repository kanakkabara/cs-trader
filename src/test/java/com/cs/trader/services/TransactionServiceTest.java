package com.cs.trader.services;

import com.cs.trader.domain.OrderSide;
import com.cs.trader.domain.OrderStatus;
import com.cs.trader.domain.OrderType;
import com.cs.trader.domain.Transaction;
import com.cs.trader.exceptions.OrderNotFoundException;
import com.cs.trader.exceptions.TraderNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TransactionServiceTest {
    @Autowired
    TransactionService transactionService;

    @Test(expected = OrderNotFoundException.class)
    public void addNewTransactionFailsIfOrderDoesNotExist(){
        Transaction t = new Transaction(0L, 100L, 1L, OrderSide.BUY, OrderType.MARKET, 10.0, 200, OrderStatus.OPEN);
        transactionService.addNewTransaction(t);
    }

    @Test(expected = TraderNotFoundException.class)
    public void addNewTransactionFailsIfTraderDoesNotExist(){
        Transaction t = new Transaction(0L, 1L, 100L, OrderSide.BUY, OrderType.MARKET, 10.0, 200, OrderStatus.OPEN);
        transactionService.addNewTransaction(t);
    }
}
