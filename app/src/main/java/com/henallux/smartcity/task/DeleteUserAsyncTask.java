package com.henallux.smartcity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.henallux.smartcity.dataAccess.UserDAO;
import com.henallux.smartcity.exception.UserException;

public class DeleteUserAsyncTask extends AsyncTask<String, Void, String> {

    private int idUser;
    private Activity activity;


    public DeleteUserAsyncTask(Activity activity, int idUser) {
        this.activity = activity;
        this.idUser = idUser;
    }

    protected String doInBackground(String... urls) {
        try {

            UserDAO.makeDeleteUserRequest(this.idUser, activity.getApplication().getApplicationContext());
        } catch (final UserException e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
