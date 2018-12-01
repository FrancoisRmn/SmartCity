package com.henallux.smartcity.ApplicationObject;

public class Application extends android.app.Application {
    private String token;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
