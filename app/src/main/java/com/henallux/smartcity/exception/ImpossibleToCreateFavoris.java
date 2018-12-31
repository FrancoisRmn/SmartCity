package com.henallux.smartcity.exception;

public class ImpossibleToCreateFavoris extends Exception {
    private String message;
    public ImpossibleToCreateFavoris(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
