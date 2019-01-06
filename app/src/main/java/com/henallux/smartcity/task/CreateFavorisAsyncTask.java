package com.henallux.smartcity.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.henallux.smartcity.dataAccess.FavorisDAO;
import com.henallux.smartcity.exception.ImpossibleToCreateFavoris;
import com.henallux.smartcity.model.Favoris;
import com.henallux.smartcity.utils.Constantes;

import static com.henallux.smartcity.utils.Constantes.URL_FAVORIS;


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
            Favoris newFavoris= new FavorisDAO(this.context).makePostCreateFavorisRequest(URL_FAVORIS,
                    favoris);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, Constantes.MESSAGE_ADD_FAVORIS, Toast.LENGTH_SHORT).show();
                }
            });
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
}
