package com.henallux.smartcity.listener;

import com.henallux.smartcity.model.Bar;
import com.henallux.smartcity.model.Market;
import com.henallux.smartcity.model.Restaurant;

public interface FragmentListener {
    void getMarket(Market market);
    void getBar(Bar bar);
    void getRestaurant(Restaurant restaurant);
}
