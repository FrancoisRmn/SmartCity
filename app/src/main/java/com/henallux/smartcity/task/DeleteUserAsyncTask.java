package com.henallux.smartcity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.henallux.smartcity.dataAccess.DeleteUser;
import com.henallux.smartcity.exception.UserException;

public class DeleteUserAsyncTask extends AsyncTask<String, Void, String> {
    private DeleteUser deleteUser;
    private int idUser;
    private Activity activity;


    public DeleteUserAsyncTask(Activity activity, int idUser) {
        this.activity = activity;
        this.idUser = idUser;
        deleteUser = new DeleteUser(activity.getApplication().getApplicationContext());
    }

    protected String doInBackground(String... urls) {
        //TODO modifier parametre constructeur USERDAO
        try {
            deleteUser.makeDeleteRequest(this.idUser);
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
