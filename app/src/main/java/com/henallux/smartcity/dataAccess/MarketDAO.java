package com.henallux.smartcity.dataAccess;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.model.Market;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MarketDAO {
    private Context context;
    private Application application;

    public MarketDAO(Context context) {
        this.context = context;
    }

    public ArrayList<Market> getAllMarkets() throws Exception{
        application =(Application)this.context;
        Log.i("Async","Début getAllCommerces");
        URL url = new URL("https://sc-nconnect.azurewebsites.net/api/Commerces?categorie=2");
        HttpsURLConnection connection =  (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("Authorization", "Bearer " + application.getToken());
        Log.i("Commerce","Bearer " + application.getToken());
        connection.setRequestMethod("GET");
        Log.i("Commerce","Status de connexion CommerceController : " + connection.getResponseCode());
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
            JSONObject jsonMarket = jsonArray.getJSONObject(i);
            Gson object = new GsonBuilder().create();
            market = object.fromJson(jsonMarket.toString(), Market.class);
            markets.add(market);
        }
        return markets;
    }
}
