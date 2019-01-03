package com.henallux.smartcity.exception;

public class ImpossibleToFetchBarsException extends Exception {
    private String message;
    public ImpossibleToFetchBarsException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
