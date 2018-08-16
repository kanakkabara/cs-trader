package com.cs.trader.dao;

import com.cs.trader.CsTraderApplication;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
public class TransactionDaoTest {
    @Autowired
    private TransactionDao transactionDao;

    @Test(expected = OrderNotFoundException.class)
    public void addNewTransactionFailsIfOrderDoesNotExist(){
        Transaction t = new Transaction(0, 100, 1, OrderSide.BUY, OrderType.MARKET, 10.0, 200, Timestamp.from(java.time.Instant.now()), OrderStatus.CREATED);
        transactionDao.addNewTransaction(t);
    }

    @Test(expected = TraderNotFoundException.class)
    public void addNewTransactionFailsIfTraderDoesNotExist(){
        Transaction t = new Transaction(0, 1, 100, OrderSide.BUY, OrderType.MARKET, 10.0, 200, Timestamp.from(java.time.Instant.now()), OrderStatus.CREATED);
        transactionDao.addNewTransaction(t);
    }

    @Test()
    public void addNewTransactionSuccess(){
        Transaction t = new Transaction(0, 1, 1, OrderSide.BUY, OrderType.MARKET, 10.0, 200, Timestamp.from(java.time.Instant.now()), OrderStatus.CREATED);
        int newID = transactionDao.addNewTransaction(t);
        assertEquals(1, newID);
    }
}
