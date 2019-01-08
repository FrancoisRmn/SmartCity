package com.henallux.smartcity.applicationObject;

public class Application extends android.app.Application {

    private String token;
    private boolean isConnected;
    private boolean activateNotifications;

    public boolean isActivateNotifications() {
        return activateNotifications;
    }

    public void setActivateNotifications(boolean activateNotifications) {
        this.activateNotifications = activateNotifications;
    }

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
