package com.henallux.smartcity.dataAccess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.exception.ImpossibleToCreateUser;
import com.henallux.smartcity.model.User;
import com.henallux.smartcity.view.BottomMenu;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.henallux.smartcity.utils.Constantes.URL_TOKEN;

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

    public void createUser(User user)
    {
        createUserAsyncTask = new CreateUserAsyncTask(this.applicationContext, user).execute();
    }

    private class CreateUserAsyncTask extends AsyncTask<String, Void, User> {
        private User user;
        private Context context;

        public CreateUserAsyncTask(Context context, User user) {
            this.context = context;
            this.user = user;
        }

        @Override
        protected void onPostExecute(User newUser) {
                if (newUser != null) {
                    new LoginUserAsyncTask(UserDAO.this.applicationContext, user.getUserName(), user.getPassword()).execute();
                } else {
                    UserDAO.this.mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UserDAO.this.mainActivity, "Erreur lors de la création de l'utilisateur", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
        }

        @Override
        protected User doInBackground(String... params) {
            try {
                User newUser = makePostCreateUserRequest(URL_TOKEN,
                        user);
                return newUser;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }
    }


    public User makePostCreateUserRequest(String stringUrl, User user) throws Exception {
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
        }else{
            throw new ImpossibleToCreateUser("Erreur lors de la tentative de création d'un utilisateur ! Status code : "  + responseCode);
        }
    }

    public User jsonToUser(String jsonUser)
    {
        Gson g = new Gson();
        User user = g.fromJson(jsonUser,User.class);
        return user;
    }

}
