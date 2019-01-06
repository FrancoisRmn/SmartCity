package com.henallux.smartcity.exception;

public class FavorisAlreadyExistException extends Exception {
    private String message;
    public FavorisAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
