package com.henallux.smartcity.dataAccess;

import android.content.Context;

import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.exception.ImpossibleToDeleteUserException;
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

import static com.henallux.smartcity.utils.Constantes.URL_USER;

public class DeleteUser {
    private Application application;
    private Context context;

    public DeleteUser(Context context) {
        this.context = context;
    }

    public void makeDeleteRequest () throws Exception
    {
        application =(Application)this.context;
        URL url = new URL(URL_USER);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Authorization", "Bearer " + application.getToken());
        int responseCode = connection.getResponseCode();
        if(responseCode != HttpURLConnection.HTTP_OK)
        {
            throw new ImpossibleToDeleteUserException(Constantes.ERROR_MESSAGE_DELETE_USER + ", " + Utils.getErrorMessage(responseCode));
        }
    }
}
