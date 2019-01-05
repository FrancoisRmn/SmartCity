package com.henallux.smartcity.exception;

public class ImpossibleToDeleteUserException extends Exception {
    private String message;
    public ImpossibleToDeleteUserException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
