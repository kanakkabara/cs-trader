package com.cs.trader.dao;

import com.cs.trader.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class TransactionDao {
    @Autowired
    private JdbcTemplate jdbc;

    public List<Transaction> getAllTransactions(){
        return jdbc.query("SELECT * FROM TRANSACTIONS ORDER BY TRANSACTION_TIMESTAMP DESC", new TransactionRowMapper());
    }

    public int addNewTransaction(Transaction transaction){
        String insertSql = "INSERT INTO TRANSACTIONS(ORDER_ID, TRADER_ID, ORDER_SIDE, ORDER_TYPE, PRICE, VOLUME, "
                + "TRANSACTION_TIMESTAMP, ORDER_STATUS, TRANSACTION_TYPE) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, transaction.getOrderID());
                statement.setLong(2, transaction.getTraderID());
                statement.setString(3, transaction.getOrderSide().toString());
                statement.setString(4, transaction.getOrderType().toString());
                statement.setDouble(5, transaction.getPrice());
                statement.setInt(6, transaction.getVolume());
                statement.setTimestamp(7, transaction.getTransactionTimestamp());
                statement.setString(8, transaction.getOrderStatus().toString());
                statement.setString(9, transaction.getTransactionType().toString());

                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    class TransactionRowMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transaction t = new Transaction();
            t.setTransactionID(rs.getLong("TRANSACTION_ID"));
            t.setOrderID(rs.getLong("ORDER_ID"));
            t.setTraderID(rs.getLong("TRADER_ID"));
            t.setOrderSide(OrderSide.valueOf(rs.getString("ORDER_SIDE")));
            t.setOrderType(OrderType.valueOf(rs.getString("ORDER_TYPE")));
            t.setPrice(rs.getDouble("PRICE"));
            t.setVolume(rs.getInt("VOLUME"));
            t.setTransactionTimestamp(rs.getTimestamp("TRANSACTION_TIMESTAMP"));
            t.setOrderStatus(OrderStatus.valueOf(rs.getString("ORDER_STATUS")));
            t.setTransactionType(TransactionType.valueOf(rs.getString("TRANSACTION_TYPE")));

            return t;
        }
    }
}
