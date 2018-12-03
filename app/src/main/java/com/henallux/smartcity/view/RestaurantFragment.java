package com.henallux.smartcity.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
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
import com.henallux.smartcity.model.Address;
import com.henallux.smartcity.model.Restaurant;
import com.henallux.smartcity.model.RestaurantViewModel;

import java.util.ArrayList;

public class RestaurantFragment extends Fragment {

    private RecyclerView restaurantsToDisplay;
    private ListView listViewRestaurantsToDisplay;
    private ArrayList<Restaurant> restaurants;
    private LoadRestaurants loadRestaurants;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Essai de faire une listView plus stylée en utilisant une tache synchrone venant d'une classe externe
        /*
        View view =  inflater.inflate(R.layout.fragment_restaurant, container, false);

        restaurantViewModel = ViewModelProviders.of(getActivity()).get(RestaurantViewModel.class);

        fragmentManager = getFragmentManager();
        loadRestaurantsTask = new LoadRestaurantsTask(fragmentManager, getActivity());
        loadRestaurantsTask.execute();
        restaurants = ((RestaurantViewModel)restaurantViewModel).getRestaurants();
        listViewRestaurantsToDisplay = (ListView) view.findViewById(R.id.RestaurantListView);
        final ArrayList<String> restaurantsDescriptions = arrayListRestaurantToArrayListString(restaurants);
        ArrayAdapter<String> listRestaurantAdapter= new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                restaurantsDescriptions
        );
        listViewRestaurantsToDisplay.setAdapter(listRestaurantAdapter);
        return view;
        */
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
        //version avec recycler view
        /*
        restaurantsToDisplay = view.findViewById(R.id.RestaurantRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        restaurantsToDisplay.setLayoutManager(layoutManager);
        new LoadRestaurants().execute();
         */
        //version avec listView (juste remplacer dans le layout recyclerView par ListView)
        listViewRestaurantsToDisplay = (ListView) view.findViewById(R.id.RestaurantRecyclerView);
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
                */
            }catch (Exception e){
                Log.i("Async",e.getMessage());

                e.printStackTrace();
            }
            return restaurants;
        }

        @Override
        protected void onPostExecute(ArrayList<Restaurant> restaurants) {
            /*
            RecyclerView.Adapter adapter = new RestaurantAdapter(restaurants);
            restaurantsToDisplay.setAdapter(adapter);
            */
            final ArrayList<String> restaurantsDescriptions = arrayListRestaurantToArrayListString(restaurants);
            ArrayAdapter<String> listRestaurantAdapter= new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    restaurantsDescriptions
            );
            listViewRestaurantsToDisplay.setAdapter(listRestaurantAdapter);
            //gestion des clicks
            listViewRestaurantsToDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    new ElementDetailFragment();
                    ElementDetailFragment elementDetailFragment = new ElementDetailFragment();
                    FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, elementDetailFragment).commit();
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
