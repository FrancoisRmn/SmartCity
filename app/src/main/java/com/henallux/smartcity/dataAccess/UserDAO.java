package com.henallux.smartcity.dataAccess;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.exception.BadLoginPasswordException;
import com.henallux.smartcity.exception.ImpossibleToFetchToken;
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
import javax.net.ssl.HttpsURLConnection;
import static com.henallux.smartcity.utils.Constantes.URL_USER;

public class UserDAO {
    public UserDAO() {

    }

    public static String makePostTokenRequest(String stringUrl, String payload) throws Exception {
        URL url = new URL(stringUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        String line;
        StringBuffer jsonString = new StringBuffer();

        uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        uc.setRequestMethod("POST");
        uc.setDoInput(true);
        uc.setInstanceFollowRedirects(false);
        uc.connect();
        OutputStreamWriter writer = new OutputStreamWriter(uc.getOutputStream(), "UTF-8");
        writer.write(payload);
        writer.close();
        int responseCode = uc.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while((line = br.readLine()) != null){
                jsonString.append(line);
            }
            br.close();
            uc.disconnect();
            return JsonToStringToken(jsonString.toString());
        }
        else
        {
            if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED)
                throw new BadLoginPasswordException(Constantes.ERROR_MESSAGE_LOGIN);
            else
                throw new ImpossibleToFetchToken(Constantes.ERROR_MESSAGE_TOKEN + ", " + Utils.getErrorMessage(responseCode));
        }

    }

    public static String JsonToStringToken(String stringJson) throws Exception
    {
        String token="";
        JSONObject jsonToken = new JSONObject(stringJson);
        token = jsonToken.getString("access_token");
        return token;
    }

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

    public static void makeDeleteUserRequest(int idUser, Context context) throws Exception
    {
        Application application =(com.henallux.smartcity.applicationObject.Application)context;
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
