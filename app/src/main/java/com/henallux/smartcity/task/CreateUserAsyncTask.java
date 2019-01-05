package com.henallux.smartcity.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.henallux.smartcity.dataAccess.CreateUser;
import com.henallux.smartcity.exception.ImpossibleToCreateUser;
import com.henallux.smartcity.model.User;
import static com.henallux.smartcity.utils.Constantes.URL_USER;

public class CreateUserAsyncTask extends AsyncTask<String, Void, User> {
    private User user;
    private Context context;
    private Activity activity;

    public CreateUserAsyncTask(Context context, User user, Activity activity) {
        this.context = context;
        this.user = user;
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(User newUser) {
        if (newUser != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Utilisateur créé !", Toast.LENGTH_SHORT).show();
                }
            });
            new LoginAsyncTask(context, user.getUserName(), user.getPassword(), activity).execute();
        }
    }

    @Override
    protected User doInBackground(String... params) {
        try {
            User newUser = CreateUser.makePostCreateUserRequest(URL_USER,
                    user);
            return newUser;
        }
        catch(final ImpossibleToCreateUser e){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
