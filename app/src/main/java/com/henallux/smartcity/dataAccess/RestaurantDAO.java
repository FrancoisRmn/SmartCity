package com.henallux.smartcity.dataAccess;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class RestaurantDAO {
    private Context context;
    private Application application;
    public RestaurantDAO(Context context) {
        this.context = context;
    }

    public ArrayList<Restaurant> getAllRestaurants() throws Exception{
        application =(Application)this.context;
        URL url = new URL("https://sc-nconnect.azurewebsites.net/api/Commerces?categorie=1");
        HttpsURLConnection connection =  (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("Authorization", "Bearer " + application.getToken());
        Log.i("restaurants","Bearer " + application.getToken());
        connection.setRequestMethod("GET");
        //connection.connect();
        Log.i("restaurants","Status de connexion RestaurantsController : " + connection.getResponseCode());
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
