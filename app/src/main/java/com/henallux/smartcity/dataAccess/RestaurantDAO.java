package com.henallux.smartcity.dataAccess;

import android.util.Log;

import com.henallux.smartcity.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class RestaurantDAO {

        public ArrayList<Restaurant> getAllRestaurants() throws Exception{
            Log.i("Async","Début getAllRestaurants");


            URL url = new URL("http://localhost:5000/api/Restaurants");
            HttpsURLConnection connection =  (HttpsURLConnection)url.openConnection();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String stringJSON = "",line;
            while ((line = buffer.readLine()) != null){
                builder.append(line);
            }
            buffer.close();
            stringJSON = builder.toString();
            return jsonToRestaurants(stringJSON);
        }

        private ArrayList<Restaurant> jsonToRestaurants(String stringJSON) throws Exception{
            ArrayList<Restaurant> restaurants = new ArrayList<>();
            Restaurant restaurant;
            JSONArray jsonArray = new JSONArray(stringJSON);
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonPerson = jsonArray.getJSONObject(i);
                Gson object = new GsonBuilder().create();
                restaurant = object.fromJson(jsonPerson.toString(), Restaurant.class);
                restaurants.add(restaurant);
            }
            return restaurants;
        }
}
