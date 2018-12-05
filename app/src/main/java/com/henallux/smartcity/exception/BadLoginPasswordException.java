package com.henallux.smartcity.exception;

public class BadLoginPasswordException extends Exception {
    private String message;
    public BadLoginPasswordException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
