package com.henallux.smartcity.dataAccess;

import android.util.Log;

import com.google.gson.Gson;
import com.henallux.smartcity.exception.UserException;
import com.henallux.smartcity.model.User;
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

public class CreateUser {
    public static User makePostCreateUserRequest(String stringUrl, User user) throws Exception {
        URL url = new URL(stringUrl);

        JSONObject postData = new JSONObject();
        postData.put("Username",user.getUserName());
        postData.put("Password",user.getPassword());
        postData.put("email",user.getEmail());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
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
        Log.i("tag","Status code : "+responseCode);
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
            return jsonToUser(builder.toString());
        }
        else
        {
            throw new UserException(Constantes.ERROR_MESSAGE_CREATE_USER + ", " + Utils.getErrorMessage(responseCode));
        }
    }

    public static User jsonToUser(String jsonUser)
    {
        Gson g = new Gson();
        User user = g.fromJson(jsonUser,User.class);
        return user;
    }
}
