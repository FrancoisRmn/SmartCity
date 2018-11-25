package com.henallux.smartcity.view;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ListView;

import com.henallux.smartcity.model.Address;
import com.henallux.smartcity.model.Restaurant;
import com.henallux.smartcity.model.RestaurantViewModel;

import java.util.ArrayList;

public class LoadRestaurantsTask extends AsyncTask<String, Void, ArrayList<Restaurant>> {
    private FragmentManager fragmentManager;
    private Activity activity;
    private RestaurantViewModel restaurantViewModel;
    private ListView listViewRestaurants;

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
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected ArrayList<Restaurant> doInBackground(String... strings) {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        Log.d("LoadRestaurantTask", "On rentre dans doInBackground");
        if (isCancelled()) {
            Log.d("doInBackground", "Call on method isCancelled() ");
        }
        else
        {
            int size = strings.length;
            if (size == 1) {
                try {
                    Address address = new Address("Rue de l'ange", "5550", 10);
                    ArrayList<String> moyensPayements = new ArrayList<String>();
                    Restaurant restaurant = new Restaurant();
                    restaurant.setNomCommerce("Burger King");
                    restaurant.setAddress(address);
                    restaurant.setDescription("Restauration rapide");
                    restaurant.setMail("burgerking@yahoo.com");
                    restaurants.add(restaurant );

                    Address address2 = new Address("Rue de fer", "5550", 13);
                    Restaurant restaurant2 = new Restaurant();
                    restaurant2.setNomCommerce("Pizza hut");
                    restaurant2.setAddress(address2);
                    restaurant2.setDescription("");
                    restaurant2.setMail("pizza_hut@yahoo.com");
                    restaurants.add(restaurant2);

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            else
            {
                restaurants.add(new Restaurant("Pas de localite trouve"));
            }
        }
        Log.d("LoadRestaurantTask", "On retourne les restaurants");
        restaurantViewModel.setRestaurants(restaurants);
        return restaurants;
    }

    @Override
    protected void onPostExecute(ArrayList<Restaurant> restaurants) {
        //on ajoute a RestaurantViewModel pour la récupérer dans restaurantsFragment
        Log.d("LoadRestaurantTask", "On rentre dans onPostExecute");
        restaurantViewModel.setRestaurants(restaurants);
    }
}
