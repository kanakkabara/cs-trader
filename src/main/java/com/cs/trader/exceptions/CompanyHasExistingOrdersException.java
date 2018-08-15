package com.cs.trader.exceptions;

public class CompanyHasExistingOrdersException extends RuntimeException {
    public CompanyHasExistingOrdersException(String s) {
        super(s);
    }
}
