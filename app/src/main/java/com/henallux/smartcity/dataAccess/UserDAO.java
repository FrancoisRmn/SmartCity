package com.henallux.smartcity.dataAccess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.henallux.smartcity.ApplicationObject.Application;
import com.henallux.smartcity.view.BottomMenu;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public static String makePostRequest(String stringUrl, String payload) throws Exception {
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

    public void loginWithoutConnection() {
        String userLambda ="janedoe";
        String passwordLambda="123";
        getUserAsyncTask = new GetUserAsyncTask(this.applicationContext, userLambda, passwordLambda).execute();
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
        protected void onPostExecute(String response){
            application = (Application)this.context;
            if(!response.isEmpty())
            {
                application.setToken(response);
                Intent intent = new Intent(UserDAO.this.mainActivity,BottomMenu.class);
                UserDAO.this.mainActivity.startActivity(intent);
            }
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
            }
            catch (FileNotFoundException e){
                Toast.makeText(UserDAO.this.mainActivity, "Combinaison login/ mdp incorrecte", Toast.LENGTH_SHORT).show();
                return "";
            }
            catch (IOException ex) {
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
