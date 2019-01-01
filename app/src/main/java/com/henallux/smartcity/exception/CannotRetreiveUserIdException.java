package com.henallux.smartcity.exception;

public class CannotRetreiveUserIdException extends Exception {
    private String message;
    public CannotRetreiveUserIdException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
