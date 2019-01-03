package com.henallux.smartcity.exception;

public class ImpossibleToFetchToken extends Exception {
    private String message;
    public ImpossibleToFetchToken(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
