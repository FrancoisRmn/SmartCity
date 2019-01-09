package com.henallux.smartcity.exception;

public class UnauthorizedException extends Exception {
    private String message;
    public UnauthorizedException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
