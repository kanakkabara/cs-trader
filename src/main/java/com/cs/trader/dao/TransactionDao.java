package com.cs.trader.dao;

import com.cs.trader.domain.Transaction;
import com.cs.trader.services.OrderService;
import com.cs.trader.services.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class TransactionDao {
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TraderService traderService;

    public int addNewTransaction(Transaction transaction){
        orderService.retrieveOrderByOrderId(transaction.getOrderID());
        traderService.findTraderById(transaction.getTraderID());

        String insertSql = "INSERT INTO TRANSACTIONS(ORDER_ID, TRADER_ID, INSTRUCTION, ORDER_TYPE, PRICE, VOLUME, "
                + "TRANSACTION_TIMESTAMP, STATUS) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, transaction.getOrderID());
                statement.setLong(2, transaction.getTraderID());
                statement.setString(3, transaction.getSide().toString());
                statement.setString(4, transaction.getOrderType().toString());
                statement.setDouble(5, transaction.getPrice());
                statement.setInt(6, transaction.getVolume());
                statement.setTimestamp(7, transaction.getTransactionTimestamp());
                statement.setString(8, transaction.getStatus().toString());

                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }
}
