package com.cs.trader.domain;

import java.sql.Timestamp;

public class Transaction {
    private int transactionID;
    private int orderID;
    private int traderID;

    private OrderInstruction instruction;
    private OrderType orderType;
    private Double price;
    private int volume;
    private Timestamp transactionTimestamp;
    private OrderStatus status;

    public Transaction(int transactionID, int orderID, int traderID, OrderInstruction instruction, OrderType orderType, Double price, int volume, Timestamp transactionTimestamp, OrderStatus status) {
        this.transactionID = transactionID;
        this.orderID = orderID;
        this.traderID = traderID;
        this.instruction = instruction;
        this.orderType = orderType;
        this.price = price;
        this.volume = volume;
        this.transactionTimestamp = transactionTimestamp;
        this.status = status;
    }

    public Transaction() {
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getTraderID() {
        return traderID;
    }

    public void setTraderID(int traderID) {
        this.traderID = traderID;
    }

    public OrderInstruction getInstruction() {
        return instruction;
    }

    public void setInstruction(OrderInstruction instruction) {
        this.instruction = instruction;
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
