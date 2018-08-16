package com.cs.trader.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateTickerException extends RuntimeException {
    public DuplicateTickerException(String s) {
        super(s);
    }
}
