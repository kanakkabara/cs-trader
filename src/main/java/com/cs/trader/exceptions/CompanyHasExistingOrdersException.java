package com.cs.trader.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CompanyHasExistingOrdersException extends RuntimeException {
    public CompanyHasExistingOrdersException(String s) {
        super(s);
    }
}
