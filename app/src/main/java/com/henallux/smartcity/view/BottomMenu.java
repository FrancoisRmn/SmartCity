package com.henallux.smartcity.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;

import com.henallux.smartcity.R;
import com.henallux.smartcity.listener.FragmentListener;
import com.henallux.smartcity.model.Bar;
import com.henallux.smartcity.model.Market;
import com.henallux.smartcity.model.Restaurant;

public class BottomMenu extends AppCompatActivity implements FragmentListener {
    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_menu);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RestaurantFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_eat:
                            selectedFragment = new RestaurantFragment();
                            break;
                        case R.id.nav_drink:
                            selectedFragment = new DrinkFragment();
                            break;
                        case R.id.nav_market:
                            selectedFragment = new MarketFragment();
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void getMarket(Market market) {
        Fragment elementDetailFragmentMarket = new ElementDetailFragmentMarket();
        ((ElementDetailFragmentMarket) elementDetailFragmentMarket).setData(market);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, elementDetailFragmentMarket).commit();
    }

    @Override
    public void getBar(Bar bar) {
        Fragment elementDetailFragmentBar = new ElementDetailFragmentBar();
        ((ElementDetailFragmentBar) elementDetailFragmentBar).setData(bar);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, elementDetailFragmentBar).commit();
    }

    @Override
    public void getRestaurant(Restaurant restaurant) {
        Fragment elementDetailFragmentRestaurant= new ElementDetailFragmentRestaurant();
        ((ElementDetailFragmentRestaurant) elementDetailFragmentRestaurant).setData(restaurant);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, elementDetailFragmentRestaurant).commit();
    }
}
