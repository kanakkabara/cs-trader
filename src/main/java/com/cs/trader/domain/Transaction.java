package com.cs.trader.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    private Long transactionID;
    private TransactionType transactionType;

    private Long orderID;
    private Long traderID;
    private OrderSide orderSide;
    private OrderType orderType;
    private Double price;
    private Integer volume;

    private Timestamp transactionTimestamp;
    private OrderStatus orderStatus;

    public Transaction(){}

    public Transaction(Long transactionID, Long orderID, Long traderID, OrderSide orderSide, OrderType orderType, Double price, Integer volume, OrderStatus orderStatus) {
        this.transactionID = transactionID;
        this.orderID = orderID;
        this.traderID = traderID;
        this.orderSide = orderSide;
        this.orderType = orderType;
        this.price = price;
        this.volume = volume;
        this.orderStatus = orderStatus;

        this.transactionTimestamp = Timestamp.from(java.time.Instant.now());
        this.transactionType = TransactionType.CREATED;
    }

    public Transaction(Order order, TransactionType type) {
        this.orderID = order.getOrderId();
        this.traderID = order.getTraderId();
        this.orderSide = order.getSide();
        this.orderType = order.getType();
        this.price = order.getPrice();
        this.volume = order.getVolume();
        this.orderStatus = order.getStatus();

        this.transactionTimestamp = Timestamp.from(java.time.Instant.now());
        this.transactionType = type;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Long getTraderID() {
        return traderID;
    }

    public void setTraderID(Long traderID) {
        this.traderID = traderID;
    }

    public OrderSide getOrderSide() {
        return orderSide;
    }

    public void setOrderSide(OrderSide orderSide) {
        this.orderSide = orderSide;
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

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Timestamp getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(Timestamp transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
