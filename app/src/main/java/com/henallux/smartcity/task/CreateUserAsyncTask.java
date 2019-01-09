package com.henallux.smartcity.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.henallux.smartcity.dataAccess.UserDAO;
import com.henallux.smartcity.exception.UnauthorizedException;
import com.henallux.smartcity.exception.UserException;
import com.henallux.smartcity.model.User;
import com.henallux.smartcity.utils.Constantes;
import com.henallux.smartcity.view.MainActivity;

import static com.henallux.smartcity.utils.Constantes.URL_USER;

public class CreateUserAsyncTask extends AsyncTask<String, Void, User> {
    private User user;
    private Activity activity;

    public CreateUserAsyncTask(User user, Activity activity) {
        this.user = user;
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(User newUser) {
        if (newUser != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, Constantes.MESSAGE_USER_CREATED, Toast.LENGTH_SHORT).show();
                }
            });
            new LoginAsyncTask(user.getUserName(), user.getPassword(), activity).execute();
        }
    }

    @Override
    protected User doInBackground(String... params) {
        try {
            User newUser = UserDAO.makePostCreateUserRequest(URL_USER,
                    user);
            return newUser;
        }
        catch(final UnauthorizedException e){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            activity.startActivity(new Intent(activity, MainActivity.class));
            return null;
        }
        catch(final UserException e){
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

