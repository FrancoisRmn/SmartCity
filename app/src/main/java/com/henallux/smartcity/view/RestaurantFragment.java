package com.henallux.smartcity.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.RestaurantDAO;
import com.henallux.smartcity.listener.FragmentListener;
import com.henallux.smartcity.model.Restaurant;

import java.util.ArrayList;

public class RestaurantFragment extends Fragment {

    private ListView listViewRestaurantsToDisplay;
    private LoadRestaurants loadRestaurants;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant, container, false);

    }

    private ArrayList<String> arrayListRestaurantToArrayListString(ArrayList<Restaurant> restaurantsArrayList) {
        ArrayList<String> restaurants = new ArrayList<String>();
        for(Restaurant restaurant : restaurantsArrayList)
        {
            restaurants.add(restaurant.toString());
        }
        return restaurants;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        listViewRestaurantsToDisplay = view.findViewById(R.id.RestaurantRecyclerView);
        loadRestaurants = new LoadRestaurants();
        loadRestaurants.execute();
    }



    private class LoadRestaurants extends AsyncTask<String, Void, ArrayList<Restaurant>>{
        private RestaurantDAO restaurantDAO;

        protected ArrayList<Restaurant> doInBackground(String... urls){
            restaurantDAO = new RestaurantDAO(getActivity().getApplicationContext());

            ArrayList<Restaurant> restaurants = new ArrayList<>();
            try{
                restaurants = restaurantDAO.getAllRestaurants();
                /*

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
                */
            }catch (Exception e){
                Log.i("Async",e.getMessage());

                e.printStackTrace();
            }
            return restaurants;
        }

        @Override
        protected void onPostExecute(final ArrayList<Restaurant> restaurants) {

            final ArrayList<String> restaurantsDescriptions = arrayListRestaurantToArrayListString(restaurants);
            ArrayAdapter<String> listRestaurantAdapter= new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    restaurantsDescriptions
            );
            listViewRestaurantsToDisplay.setAdapter(listRestaurantAdapter);
            listViewRestaurantsToDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FragmentListener fragmentListener = (FragmentListener)getActivity();
                    fragmentListener.getRestaurant(restaurants.get(position));
                }
            });
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadRestaurants != null) {
            loadRestaurants.cancel(true);
        }
    }
}
