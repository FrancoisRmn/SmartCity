package com.henallux.smartcity.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.RestaurantDAO;
import com.henallux.smartcity.exception.ImpossibleToFetchCommercesException;
import com.henallux.smartcity.exception.UnauthorizedException;
import com.henallux.smartcity.listener.FragmentListener;
import com.henallux.smartcity.model.Restaurant;

import java.util.ArrayList;

public class RestaurantFragment extends Fragment {

    private ListView listViewRestaurantsToDisplay;
    private LoadRestaurants loadRestaurants;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant, container, false);

    }

    private ArrayList<String> arrayListRestaurantToArrayListString(ArrayList<Restaurant> restaurantsArrayList) {
        ArrayList<String> restaurants = new ArrayList<>();
        if (restaurantsArrayList != null) {
            for (Restaurant restaurant : restaurantsArrayList) {
                restaurants.add(restaurant.toString());
            }
        }
        return restaurants;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        listViewRestaurantsToDisplay = view.findViewById(R.id.RestaurantRecyclerView);
        loadRestaurants = new LoadRestaurants();
        if (getActivity() != null)
            loadRestaurants.execute();
        searchView = view.findViewById(R.id.searchViewRestaurant);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadRestaurants = new LoadRestaurants(query);
                loadRestaurants.execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadRestaurants = new LoadRestaurants(newText);
                loadRestaurants.execute();
                return true;
            }
        });
    }


    private class LoadRestaurants extends AsyncTask<String, Void, ArrayList<Restaurant>> {
        private RestaurantDAO restaurantDAO;
        private String query;

        private LoadRestaurants(String query) {
            this.query = query;
        }

        private LoadRestaurants() {
        }

        protected ArrayList<Restaurant> doInBackground(String... urls) {
            if (getActivity() != null)
                restaurantDAO = new RestaurantDAO(getActivity().getApplicationContext());

            ArrayList<Restaurant> restaurants = new ArrayList<>();
            try {
                if (this.query == null) {
                    restaurants = restaurantDAO.getAllRestaurants("");
                } else {
                    restaurants = restaurantDAO.getAllRestaurants(this.query);
                }
            }
            catch(final UnauthorizedException e){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
            }
            catch (final ImpossibleToFetchCommercesException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            } catch (final Exception e) {
                System.out.println(e.getMessage());
            }
            return restaurants;
        }

        @Override
        protected void onPostExecute(final ArrayList<Restaurant> restaurants) {
            if (restaurants != null) {
                final ArrayList<String> restaurantsDescriptions = arrayListRestaurantToArrayListString(restaurants);
                if (getActivity() != null && restaurantsDescriptions != null) {
                    ArrayAdapter<String> listRestaurantAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            restaurantsDescriptions
                    );
                    listViewRestaurantsToDisplay.setAdapter(listRestaurantAdapter);
                    listViewRestaurantsToDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            FragmentListener fragmentListener = (FragmentListener) getActivity();
                            fragmentListener.getRestaurant(restaurants.get(position));
                        }
                    });
                }
            }
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
