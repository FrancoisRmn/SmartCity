package com.henallux.smartcity.exception;

public class ImpossibleToCreateUser extends Exception {
    private String message;
    public ImpossibleToCreateUser(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
