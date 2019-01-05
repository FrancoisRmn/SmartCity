package com.henallux.smartcity.dataAccess;

import android.content.Context;

import com.google.gson.Gson;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.exception.ImpossibleToCreateFavoris;
import com.henallux.smartcity.model.Favoris;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateFavoris {
    private Application application;
    private Context context;

    public CreateFavoris(Context context) {
        this.context = context;
    }

    public Favoris makePostCreateFavorisRequest(String stringUrl, Favoris favoris) throws Exception {
        application = (Application)this.context;
        URL url = new URL(stringUrl);

        JSONObject postData = new JSONObject();
        postData.put("idCommerce",favoris.getIdCommerce());
        postData.put("idUser",favoris.getIdUser());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Authorization", "Bearer " + application.getToken());
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os,"UTF-8")
        );

        writer.write(postData.toString());

        writer.flush();
        writer.close();
        os.close();

        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_CREATED)
        {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder("");
            String line = "";
            while((line = buffer.readLine())!=null)
            {
                builder.append(line);
                break;
            }
            buffer.close();
            //return builder.toString();
            return jsonToFavoris(builder.toString());
        }else{
            throw new ImpossibleToCreateFavoris(" Status code : "  + responseCode + connection.getResponseMessage());
        }
    }

    public static Favoris jsonToFavoris(String jsonUser)
    {
        Gson g = new Gson();
        Favoris favoris = g.fromJson(jsonUser,Favoris.class);
        return favoris;
    }
}
