package com.henallux.smartcity.dataAccess;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.R;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.exception.ImpossibleToFetchCommercesException;
import com.henallux.smartcity.model.Bar;
import com.henallux.smartcity.utils.Constantes;
import com.henallux.smartcity.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class BarDAO {
    private Context context;
    private Application application;

    public BarDAO(Context context) {
        this.context = context;
    }

    public ArrayList<Bar> getAllBars(String query) throws Exception{
        application =(Application)this.context;
        URL url;
        if(!query.equals("")){
            url = new URL(context.getString(R.string.URL_GET_BARS) + "&nom="+query);
        }
        else{
            url = new URL(context.getString(R.string.URL_GET_BARS));
        }
        HttpsURLConnection connection =  (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("Authorization", "Bearer " + application.getToken());
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String stringJSON = "",line;
            while ((line = buffer.readLine()) != null){
                builder.append(line);
            }
            buffer.close();
            stringJSON = builder.toString();
            return jsonToBars(stringJSON);
        }
        else{
            throw new ImpossibleToFetchCommercesException(Constantes.ERROR_MESSAGE_BARS + " ," + Utils.getErrorMessage(responseCode));
        }
    }

    private ArrayList<Bar> jsonToBars(String stringJSON) throws Exception{
        ArrayList<Bar> bars = new ArrayList<>();
        Bar bar;
        JSONArray jsonArray = new JSONArray(stringJSON);
        for (int i=0; i<jsonArray.length(); i++){
            JSONObject jsonBar = jsonArray.getJSONObject(i);
            Gson object = new GsonBuilder().create();
            bar = object.fromJson(jsonBar.toString(), Bar.class);
            bars.add(bar);
        }
        return bars;
    }
}
