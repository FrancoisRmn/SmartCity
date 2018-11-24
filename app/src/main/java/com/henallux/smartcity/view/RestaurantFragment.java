package com.henallux.smartcity.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.RestaurantDAO;
import com.henallux.smartcity.model.Address;
import com.henallux.smartcity.model.Restaurant;

import java.util.ArrayList;

public class RestaurantFragment extends Fragment {

    private RecyclerView restaurantsToDisplay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        restaurantsToDisplay = view.findViewById(R.id.RestaurantRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        restaurantsToDisplay.setLayoutManager(layoutManager);
        new LoadRestaurants().execute();
    }

    private class LoadRestaurants extends AsyncTask<String, Void, ArrayList<Restaurant>>{
        private RestaurantDAO restaurantDAO;

        protected ArrayList<Restaurant> doInBackground(String... urls){
            restaurantDAO = new RestaurantDAO();

            ArrayList<Restaurant> restaurants = new ArrayList<>();
            try{
                //restaurants = restaurantDAO.getAllRestaurants();
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
            }catch (Exception e){
                Log.i("Async",e.getMessage());

                e.printStackTrace();
            }
            Log.i("Async","Fin de doInBackGround");
            return restaurants;
        }

        @Override
        protected void onPostExecute(ArrayList<Restaurant> restaurants) {
            RecyclerView.Adapter adapter = new RestaurantAdapter(restaurants);
            restaurantsToDisplay.setAdapter(adapter);
        }
    }
}
