package com.henallux.smartcity.exception;

public class UserException extends Exception {
    private String message;
    public UserException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
