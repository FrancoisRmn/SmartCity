package com.henallux.smartcity.exception;

public class ImpossibleToFetchMarketsException extends Exception {
    private String message;
    public ImpossibleToFetchMarketsException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
