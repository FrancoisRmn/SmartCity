package com.henallux.smartcity.dataAccess;

import android.content.Context;

import com.google.gson.Gson;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.exception.FavorisAlreadyExistException;
import com.henallux.smartcity.exception.ImpossibleToCreateFavoris;
import com.henallux.smartcity.exception.ImpossibleToDeleteFavoris;
import com.henallux.smartcity.model.Favoris;
import com.henallux.smartcity.utils.Constantes;
import com.henallux.smartcity.utils.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.henallux.smartcity.utils.Constantes.ERROR_MESSAGE_FAVORIS;

public class DeleteFavoris {
    private Application application;
    private Context context;

    public DeleteFavoris(Context context) {
        this.context = context;
    }

    public void makePostDeleteFavorisRequest(String stringUrl, Favoris favoris) throws Exception {
        application = (Application)this.context;
        URL url = new URL(stringUrl);

        JSONObject postData = new JSONObject();
        postData.put("idCommerce",favoris.getIdCommerce());
        postData.put("idUser",favoris.getIdUser());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
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
        if(responseCode == HttpURLConnection.HTTP_OK)
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
        }
        else{
            if(responseCode == HttpURLConnection.HTTP_BAD_REQUEST){
                throw new FavorisAlreadyExistException(Constantes.FAVORIS_DONT_EXIST);
            }
            else{
                throw new ImpossibleToDeleteFavoris(ERROR_MESSAGE_FAVORIS + Utils.getErrorMessage(responseCode));
            }
        }
    }
}
