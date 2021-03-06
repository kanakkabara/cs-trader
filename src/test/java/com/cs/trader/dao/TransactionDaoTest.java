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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsTraderApplication.class)
@Transactional
public class TransactionDaoTest {
    @Autowired
    private TransactionDao transactionDao;

    @Test(expected = DataIntegrityViolationException.class)
    public void addNewTransactionFailsIfOrderDoesNotExist(){
        Transaction t = new Transaction(0L, 100L, 1L, OrderSide.BUY, OrderType.MARKET, 10.0, 200, OrderStatus.OPEN);
        transactionDao.addNewTransaction(t);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addNewTransactionFailsIfTraderDoesNotExist(){
        Transaction t = new Transaction(0L, 1L, 100L, OrderSide.BUY, OrderType.MARKET, 10.0, 200, OrderStatus.OPEN);
        transactionDao.addNewTransaction(t);
    }

    @Test()
    public void addNewTransactionSuccess(){
        Transaction t = new Transaction(0L, 1L, 1L, OrderSide.BUY, OrderType.MARKET, 10.0, 200, OrderStatus.OPEN);
        int newID = transactionDao.addNewTransaction(t);
        assertEquals(2, newID);
    }
}
