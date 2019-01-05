package com.henallux.smartcity.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.dataAccess.GetToken;
import com.henallux.smartcity.dataAccess.UserDAO;
import com.henallux.smartcity.exception.ImpossibleToFetchToken;
import com.henallux.smartcity.view.BottomMenu;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.henallux.smartcity.utils.Constantes.URL_TOKEN;

public class LoginAsyncTask extends AsyncTask<String, Void, String> {
    private Context context;
    private Application application;
    private String username;
    private String password;
    private Activity activity;

    public LoginAsyncTask(Context context, String username, String password, Activity activity) {
        this.context = context;
        this.username = username;
        this.password = password;
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(String response){
        application = (Application)this.context;
        if(!response.isEmpty())
        {
            application.setToken(response);
            Intent intent = new Intent(activity,BottomMenu.class);
            activity.startActivity(intent);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String response = GetToken.makePostRequest(URL_TOKEN,
                    "{\n" +
                            "\t\"Username\":\""+this.username+"\",\n" +
                            "\t\"Password\":\""+this.password+"\"\n" +
                            "}");

            return response;
        }
        catch(final ImpossibleToFetchToken e){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return "";
        }
        catch (FileNotFoundException e){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Combinaison login/ mdp incorrecte", Toast.LENGTH_SHORT).show();
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
