package com.henallux.smartcity.exception;

public class ImpossibleToFetchRestaurantsException extends Exception {
    private String message;
    public ImpossibleToFetchRestaurantsException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
