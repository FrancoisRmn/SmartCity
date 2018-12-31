package com.henallux.smartcity.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.henallux.smartcity.dataAccess.CreateFavoris;
import com.henallux.smartcity.exception.ImpossibleToCreateFavoris;
import com.henallux.smartcity.model.Favoris;

import static com.henallux.smartcity.utils.Constantes.URL_CREATE_FAVORIS;

public class CreateFavorisAsyncTask extends AsyncTask<String, Void, Favoris> {
    private Context context;
    private Activity activity;
    private Favoris favoris;

    public CreateFavorisAsyncTask(Context context, Activity activity, Favoris favoris) {
        this.context = context;
        this.activity = activity;
        this.favoris = favoris;
    }

    @Override
    protected Favoris doInBackground(String... params) {
        try {
            Favoris newFavoris= new CreateFavoris(this.context).makePostCreateFavorisRequest(URL_CREATE_FAVORIS,
                    favoris);
            return newFavoris;
        }
        catch(final ImpossibleToCreateFavoris e){
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

    @Override
    protected void onPostExecute(Favoris newFavoris) {
        if (newFavoris != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Commerce ajouté aux favoris", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
