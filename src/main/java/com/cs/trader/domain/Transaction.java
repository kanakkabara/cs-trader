package com.cs.trader.domain;

import java.sql.Timestamp;

public class Transaction {
    private long transactionID;
    private long orderID;
    private long traderID;

    private OrderSide side;
    private OrderType orderType;
    private Double price;
    private int volume;
    private Timestamp transactionTimestamp;
    private OrderStatus status;

    public Transaction(int transactionID, int orderID, int traderID, OrderSide instruction, OrderType orderType, Double price, int volume, Timestamp transactionTimestamp, OrderStatus status) {
        this.transactionID = transactionID;
        this.orderID = orderID;
        this.traderID = traderID;
        this.side = instruction;
        this.orderType = orderType;
        this.price = price;
        this.volume = volume;
        this.transactionTimestamp = transactionTimestamp;
        this.status = status;
    }

//    public Transaction(Order order, TransactionType type) {
//        this.orderID = order.getOrderId();
//        this.traderID = order.getTraderId();
//        this.side = order.getSide();
//        this.orderType = orderType;
//        this.price = price;
//        this.volume = volume;
//        this.transactionTimestamp = transactionTimestamp;
//        this.status = status;
//    }

    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public long getTraderID() {
        return traderID;
    }

    public void setTraderID(long traderID) {
        this.traderID = traderID;
    }

    public OrderSide getSide() {
        return side;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Timestamp getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(Timestamp transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
