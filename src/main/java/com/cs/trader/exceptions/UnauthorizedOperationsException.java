package com.cs.trader.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnauthorizedOperationsException extends RuntimeException{
    public UnauthorizedOperationsException(String msg) {
        super(msg);
    }
}
