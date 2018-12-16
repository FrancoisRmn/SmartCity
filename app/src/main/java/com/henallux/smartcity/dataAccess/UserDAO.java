package com.henallux.smartcity.dataAccess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.ApplicationObject.Application;
import com.henallux.smartcity.model.User;
import com.henallux.smartcity.view.BottomMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.henallux.smartcity.Utils.Constantes.URL_CREATE_USER;
import static com.henallux.smartcity.Utils.Constantes.URL_TOKEN;

public class UserDAO {
    private AsyncTask loginUserAsyncTask;
    private AsyncTask createUserAsyncTask;
    private Context applicationContext;
    private Activity mainActivity;

    public UserDAO(Context applicationContext, Activity mainActivity) {
        this.applicationContext = applicationContext;
        this.mainActivity = mainActivity;
    }

    public void login(String username, String password){
        Log.i("Login","Début de login dans UserDAO");
        loginUserAsyncTask = new LoginUserAsyncTask(this.applicationContext, username, password).execute();
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
       // try {
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while((line = br.readLine()) != null){
                jsonString.append(line);
            }
            br.close();
        //} catch (Exception ex) {
          //  ex.printStackTrace();
        //}
        uc.disconnect();
        Log.i("Login",jsonString.toString());
        //return jsonString.toString();
        return JsonToStringToken(jsonString.toString());
    }

    public void loginWithoutConnection() {
        String userLambda ="janedoe";
        String passwordLambda="123";
        loginUserAsyncTask = new LoginUserAsyncTask(this.applicationContext, userLambda, passwordLambda).execute();
    }

    public static String JsonToStringToken(String stringJson) throws Exception
    {
        String token="";
        JSONObject jsonToken = new JSONObject(stringJson);
        token = jsonToken.getString("access_token");
        return token;
    }



    private class LoginUserAsyncTask extends AsyncTask<String, Void, String> {
        private Context context;
        private Application application;
        private String username;
        private String password;

        public LoginUserAsyncTask(Context context, String username, String password) {
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
                String response = makePostRequest(URL_TOKEN,
                        "{\n" +
                                "\t\"Username\":\""+this.username+"\",\n" +
                                "\t\"Password\":\""+this.password+"\"\n" +
                                "}");

                return response;
            }
            catch (FileNotFoundException e){
                //Toast.makeText(UserDAO.this.mainActivity, "Combinaison login/ mdp incorrecte", Toast.LENGTH_SHORT).show();
                //impossible d'utiliser içi car pas le thread UI
                UserDAO.this.mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserDAO.this.mainActivity, "Combinaison login/ mdp incorrecte", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i("loginUser", "Catch FileNotFoundException  dans doInBackGound");
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

    public void createUser(String username, String password)
    {
        createUserAsyncTask = new CreateUserAsyncTask(this.applicationContext, username, password).execute();
    }

    private class CreateUserAsyncTask extends AsyncTask<String, Void, String>{
        private String username;
        private String password;
        private Context context;

        public CreateUserAsyncTask(Context context, String username, String password) {
            this.context = context;
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPostExecute(String response){
            try {
                User user;
                JSONObject jsonUser = new JSONObject(response);
                Gson object = new GsonBuilder().create();
                user = object.fromJson(jsonUser.toString(), User.class);
                if(user != null){
                    new LoginUserAsyncTask(UserDAO.this.applicationContext, user.getUserName(), user.getPassword()).execute();
                }
                else{
                    UserDAO.this.mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UserDAO.this.mainActivity, "Erreur lors de la création de l'utilisateur", Toast.LENGTH_SHORT).show();                        }
                    });


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String response = makePostCreateUserRequest(URL_CREATE_USER,
                        "{\n" +
                                "\t\"Username\":\""+this.username+"\",\n" +
                                "\t\"Password\":\""+this.password+"\"\n" +
                                "}");

                return response;
            }
            catch (FileNotFoundException e){
                //Toast.makeText(UserDAO.this.mainActivity, "Combinaison login/ mdp incorrecte", Toast.LENGTH_SHORT).show();
                UserDAO.this.mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserDAO.this.mainActivity, "Combinaison login/ mdp incorrecte", Toast.LENGTH_SHORT).show();                       }
                });
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

    public static String makePostCreateUserRequest(String stringUrl, String payload) throws Exception {
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
        return jsonString.toString();
    }

}
