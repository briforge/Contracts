package com.presto.contracts.exceptions;

public class RequestAmountExceeded extends Exception {

    public RequestAmountExceeded() {
        super("Express Contracts amount requested must be less than $50,000");
    }
}
