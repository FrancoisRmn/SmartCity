package com.henallux.smartcity.exception;

public class ImpossibleToFetchCommercesException extends Exception {
    private String message;
    public ImpossibleToFetchCommercesException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
