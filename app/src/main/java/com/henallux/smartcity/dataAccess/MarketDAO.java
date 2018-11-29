package com.henallux.smartcity.dataAccess;

import android.util.Log;

import com.henallux.smartcity.model.Market;
import com.henallux.smartcity.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MarketDAO {

    public ArrayList<Market> getAllMarkets() throws Exception{
        Log.i("Async","Début getAllCommerces");


        URL url = new URL("https://sc-nconnect.azurewebsites.net/api/Commerces");
        HttpsURLConnection connection =  (HttpsURLConnection)url.openConnection();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder builder = new StringBuilder();
        String stringJSON = "",line;
        while ((line = buffer.readLine()) != null){
            builder.append(line);
        }
        buffer.close();
        stringJSON = builder.toString();
        return jsonToCommerces(stringJSON);
    }

    private ArrayList<Market> jsonToCommerces(String stringJSON) throws Exception{
        ArrayList<Market> markets = new ArrayList<>();
        Market market;
        JSONArray jsonArray = new JSONArray(stringJSON);
        for (int i=0; i<jsonArray.length(); i++){
            JSONObject jsonPerson = jsonArray.getJSONObject(i);
            Gson object = new GsonBuilder().create();
            market = object.fromJson(jsonPerson.toString(), Market.class);
            markets.add(market);
        }
        return markets;
    }
}
