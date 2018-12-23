package com.henallux.smartcity.service;

import com.henallux.smartcity.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APINConnectService {
    @POST("Users")
    Call<User> createUser(@Body User user);
}
