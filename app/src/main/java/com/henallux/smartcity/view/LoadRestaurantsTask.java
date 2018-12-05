package com.henallux.smartcity.view;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import com.henallux.smartcity.model.Restaurant;
import com.henallux.smartcity.model.RestaurantViewModel;

import java.util.ArrayList;

public class LoadRestaurantsTask extends AsyncTask<String, Void, ArrayList<Restaurant>> {
    private FragmentManager fragmentManager;
    private Activity activity;
    private RestaurantViewModel restaurantViewModel;

    public LoadRestaurantsTask(FragmentManager fragmentManager, Activity activity) {
        super();
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        restaurantViewModel = ViewModelProviders.of((FragmentActivity) activity).get(RestaurantViewModel.class);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Restaurant> restaurants)
    {
        //((RestaurantViewModel)restaurantViewModel).setRestaurants(restaurants);
        Log.d("LoadRestaurantTask", "On rentre dans onPostExecute");
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected ArrayList<Restaurant> doInBackground(String... strings) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Log.d("LoadRestaurantTask", "On rentre dans doInBackground");

        try {
            Log.d("LoadRestaurantTask", "On ajoute les restaurants");

            ArrayList<String> moyensPayements = new ArrayList<String>();
            Restaurant restaurant = new Restaurant();
            restaurant.setNomCommerce("Burger King");

            restaurant.setDescription("Restauration rapide");
            restaurant.setMail("burgerking@yahoo.com");
            restaurants.add(restaurant );


            Restaurant restaurant2 = new Restaurant();
            restaurant2.setNomCommerce("Pizza hut");

            restaurant2.setDescription("");
            restaurant2.setMail("pizza_hut@yahoo.com");
            restaurants.add(restaurant2);

        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }

        Log.d("LoadRestaurantTask", "On retourne les restaurants");
        //restaurantViewModel.setRestaurants(restaurants);
        //onPostExecute(restaurants);
        return restaurants;
    }
}
