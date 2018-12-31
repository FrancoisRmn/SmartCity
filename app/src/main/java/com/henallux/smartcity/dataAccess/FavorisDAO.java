package com.henallux.smartcity.dataAccess;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.henallux.smartcity.model.Favoris;

public class FavorisDAO {
    private AsyncTask createFavorisAsyncTask;
    private Context applicationContext;
    private Activity mainActivity;

    public FavorisDAO(Context applicationContext, Activity mainActivity) {
        this.applicationContext = applicationContext;
        this.mainActivity = mainActivity;
    }

    public void addFavoris(Favoris favoris)
    {

    }
}
