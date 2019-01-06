package com.henallux.smartcity.model;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class RestaurantViewModel extends ViewModel {
    private Restaurant restaurant;
    private ArrayList<Restaurant> restaurants;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
