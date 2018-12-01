package com.henallux.smartcity.dataAccess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.henallux.smartcity.ApplicationObject.Application;
import com.henallux.smartcity.Exception.BadLoginPasswordException;
import com.henallux.smartcity.view.BottomMenu;
import com.henallux.smartcity.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class UserDAO {
    private AsyncTask getUserAsyncTask;
    private Context applicationContext;
    private Activity mainActivity;

    public UserDAO(Context applicationContext, Activity mainActivity) {
        this.applicationContext = applicationContext;
        this.mainActivity = mainActivity;
    }

    public void login(String username, String password){
        Log.i("Login","Début de login dans UserDAO");
        getUserAsyncTask = new GetUserAsyncTask(this.applicationContext, username, password).execute();
    }

    public static String makePostRequest(String stringUrl, String payload) throws IOException, Exception {
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
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while((line = br.readLine()) != null){
                jsonString.append(line);
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        uc.disconnect();
        Log.i("Login",jsonString.toString());
        //return jsonString.toString();
        return JsonToStringToken(jsonString.toString());
    }

    public static String JsonToStringToken(String stringJson) throws Exception
    {
        String token="";
        JSONObject jsonToken = new JSONObject(stringJson);
        token = jsonToken.getString("access_token");
        return token;
    }

    private class GetUserAsyncTask extends AsyncTask<String, Void, String> {
        private Context context;
        private Application application;
        private String username;
        private String password;

        public GetUserAsyncTask(Context context, String username, String password) {
            this.context = context;
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPostExecute(String response) {
            application = (Application)this.context;
            if(!response.isEmpty())
            {
                application.setToken(response);
                Intent intent = new Intent(UserDAO.this.mainActivity,BottomMenu.class);
                UserDAO.this.mainActivity.startActivity(intent);
            }
            /*else
            {
                throw new BadLoginPasswordException("Combinaison login / mdp incorrecte !");
            }*/
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String response = makePostRequest("https://sc-nconnect.azurewebsites.net/api/jwt",
                        "{\n" +
                                "\t\"Username\":\""+this.username+"\",\n" +
                                "\t\"Password\":\""+this.password+"\"\n" +
                                "}");

                return response;
            } catch (IOException ex) {
                ex.printStackTrace();
                return "";
            }
            catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

    }
}
