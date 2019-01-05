package com.henallux.smartcity.dataAccess;

import android.content.Context;

import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.exception.UserException;
import com.henallux.smartcity.utils.Constantes;
import com.henallux.smartcity.utils.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.henallux.smartcity.utils.Constantes.URL_USER;

public class DeleteUser {
    private Application application;
    private Context context;

    public DeleteUser(Context context) {
        this.context = context;
    }

    public void makeDeleteRequest (int idUser) throws Exception
    {
        application =(Application)this.context;
        URL url = new URL(URL_USER + "/" + idUser);
        HttpsURLConnection connection =  (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("Authorization", "Bearer " + application.getToken());
        connection.setRequestMethod("DELETE");
        int responseCode = connection.getResponseCode();
        if(responseCode != HttpURLConnection.HTTP_OK)
        {
            throw new UserException(Constantes.ERROR_MESSAGE_DELETE_USER + ", " + Utils.getErrorMessage(responseCode));
        }
        else{
            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String stringJSON = "", line;
            while ((line = buffer.readLine()) != null) {
                builder.append(line);
            }
            buffer.close();
            stringJSON = builder.toString();
        }
    }
}
