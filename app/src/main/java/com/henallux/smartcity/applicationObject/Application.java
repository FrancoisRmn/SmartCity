package com.henallux.smartcity.applicationObject;

public class Application extends android.app.Application {
    private String token;
    private boolean isConnected;

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

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
