package com.henallux.smartcity.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
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
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RestaurantFragment()).commit();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new RestaurantFragment());
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemHomeButtonId : Intent intent = new Intent(BottomMenu.this,MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    String tag="";
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_eat:
                            selectedFragment = new RestaurantFragment();
                            tag="AddFragRestaurant";
                            break;
                        case R.id.nav_drink:
                            selectedFragment = new DrinkFragment();
                            tag="AddFragBar";
                            break;
                        case R.id.nav_market:
                            selectedFragment = new MarketFragment();
                            tag="AddFragMarket";
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            tag="AddFragSettings";
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, selectedFragment);
                    transaction.commit();
                    return true;
                }
            };

    @Override
    public void getMarket(Market market) {
        Fragment elementDetailFragmentMarket = new ElementDetailFragmentMarket();
        ((ElementDetailFragmentMarket) elementDetailFragmentMarket).setData(market);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, elementDetailFragmentMarket);
        fragmentTransaction.addToBackStack("elementDetailFragmentMarket");
        fragmentTransaction.commit();
    }

    @Override
    public void getBar(Bar bar) {
        Fragment elementDetailFragmentBar = new ElementDetailFragmentBar();
        ((ElementDetailFragmentBar) elementDetailFragmentBar).setData(bar);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, elementDetailFragmentBar);
        fragmentTransaction.addToBackStack("elementDetailFragmentBar");
        fragmentTransaction.commit();
    }

    @Override
    public void getRestaurant(Restaurant restaurant) {
        Fragment elementDetailFragmentRestaurant= new ElementDetailFragmentRestaurant();
        ((ElementDetailFragmentRestaurant) elementDetailFragmentRestaurant).setData(restaurant);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, elementDetailFragmentRestaurant);
        fragmentTransaction.addToBackStack("elementDetailFragmentRestaurant");
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
        }
    }
}
