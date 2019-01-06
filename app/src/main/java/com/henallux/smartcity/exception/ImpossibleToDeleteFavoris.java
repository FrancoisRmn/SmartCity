package com.henallux.smartcity.exception;

import com.henallux.smartcity.model.Favoris;

public class ImpossibleToDeleteFavoris extends Exception {
    private String message;
    public ImpossibleToDeleteFavoris(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
