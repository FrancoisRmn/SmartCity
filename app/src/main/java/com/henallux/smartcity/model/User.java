package com.henallux.smartcity.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String password;
    private String email;
    private Integer numeroTelephone;

    public User(String userName, String password, String email, Integer numeroTelephone) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.numeroTelephone =  numeroTelephone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
