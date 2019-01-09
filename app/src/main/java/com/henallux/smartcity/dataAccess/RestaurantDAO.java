package com.henallux.smartcity.dataAccess;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.henallux.smartcity.R;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.exception.ImpossibleToFetchCommercesException;
import com.henallux.smartcity.exception.UnauthorizedException;
import com.henallux.smartcity.model.Restaurant;
import com.henallux.smartcity.utils.Constantes;
import com.henallux.smartcity.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import javax.net.ssl.HttpsURLConnection;

public class RestaurantDAO {
    private Context context;
    private Application application;
    public RestaurantDAO(Context context) {
        this.context = context;
    }

    public ArrayList<Restaurant> getAllRestaurants(String query) throws Exception{
        application =(Application)this.context;
        URL url;
        if(!query.equals("")){
            url = new URL(context.getString(R.string.URL_GET_RESTAURANTS) + "&nom="+query);
        }
        else{
            url = new URL(context.getString(R.string.URL_GET_RESTAURANTS));
        }
        HttpsURLConnection connection =  (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("Authorization", "Bearer " + application.getToken());
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String stringJSON = "", line;
            while ((line = buffer.readLine()) != null) {
                builder.append(line);
            }
            buffer.close();
            connection.disconnect();
            stringJSON = builder.toString();
            return jsonToRestaurants(stringJSON);
        }
        else{
            if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED){
                throw new UnauthorizedException("Votre session est expiré !" + ", " + Utils.getErrorMessage(responseCode));
            }
            else{
                throw new ImpossibleToFetchCommercesException(Constantes.ERROR_MESSAGE_RESTAURANT + ", " + Utils.getErrorMessage(responseCode));
            }
        }
    }

        private ArrayList<Restaurant> jsonToRestaurants(String stringJSON) throws Exception{
            ArrayList<Restaurant> restaurants = new ArrayList<>();
            Restaurant restaurant;
            JSONArray jsonArray = new JSONArray(stringJSON);
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonRestaurant = jsonArray.getJSONObject(i);
                //Gson object = new GsonBuilder().setDateFormat("HH:mm:ss").create();
                //final Gson object = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
                GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Date.class, new Utils.DateDeserializer());
                restaurant = gsonBuilder.create().fromJson(jsonRestaurant.toString(), Restaurant.class);

                restaurants.add(restaurant);
            }
            return restaurants;
        }


}
