package com.henallux.smartcity.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.dataAccess.UserDAO;
import com.henallux.smartcity.exception.BadLoginPasswordException;
import com.henallux.smartcity.exception.ImpossibleToFetchToken;
import com.henallux.smartcity.view.BottomMenu;

import static com.henallux.smartcity.utils.Constantes.URL_TOKEN;

public class LoginAsyncTask extends AsyncTask<String, Void, String> {
    private Application application;
    private String username;
    private String password;
    private Activity activity;

    public LoginAsyncTask(String username, String password, Activity activity) {
        this.username = username;
        this.password = password;
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(String response){
        application = (Application)this.activity.getApplication().getApplicationContext();
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
            String response = UserDAO.makePostTokenRequest(URL_TOKEN,
                    "{\n" +
                            "\t\"Username\":\""+this.username+"\",\n" +
                            "\t\"Password\":\""+this.password+"\"\n" +
                            "}");

            return response;
        }
        catch (final BadLoginPasswordException e){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return "";
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
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
