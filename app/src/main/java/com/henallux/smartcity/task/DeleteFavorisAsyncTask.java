package com.henallux.smartcity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.henallux.smartcity.dataAccess.FavorisDAO;
import com.henallux.smartcity.exception.FavorisAlreadyExistException;
import com.henallux.smartcity.exception.ImpossibleToDeleteFavoris;
import com.henallux.smartcity.model.Favoris;
import com.henallux.smartcity.utils.Constantes;

import static com.henallux.smartcity.utils.Constantes.URL_FAVORIS;

public class DeleteFavorisAsyncTask extends AsyncTask<String, Void, String> {
    private Activity activity;
    private Favoris favoris;

    public DeleteFavorisAsyncTask(Activity activity, Favoris favoris) {
        this.activity = activity;
        this.favoris = favoris;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            new FavorisDAO(activity.getApplication().getApplicationContext()).makePostDeleteFavorisRequest(URL_FAVORIS,
                    favoris);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, Constantes.MESSAGE_DELETE_FAVORIS, Toast.LENGTH_SHORT).show();
                }
            });

            return null;
        }
        catch(final FavorisAlreadyExistException e){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
        catch(final ImpossibleToDeleteFavoris e){
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
