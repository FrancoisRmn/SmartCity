package com.henallux.smartcity.dataAccess;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.henallux.smartcity.model.User;
import com.henallux.smartcity.task.CreateUserAsyncTask;
import com.henallux.smartcity.task.LoginAsyncTask;

public class UserDAO {
    private AsyncTask loginUserAsyncTask;
    private AsyncTask createUserAsyncTask;
    private Context applicationContext;
    private Activity mainActivity;

    public UserDAO(Context applicationContext, Activity mainActivity) {
        this.applicationContext = applicationContext;
        this.mainActivity = mainActivity;
    }

    public void login(String username, String password){
        Log.i("Login","Début de login dans UserDAO");
        loginUserAsyncTask = new LoginAsyncTask(this.applicationContext, username, password, UserDAO.this.mainActivity).execute();
    }



    public void loginWithoutConnection() {
        //TODO déplacer ça dans les constantes et pas utiliser janedoe vu que c'est un admin
        String userLambda ="janedoe";
        String passwordLambda="123";
        loginUserAsyncTask = new LoginAsyncTask(this.applicationContext, userLambda, passwordLambda, UserDAO.this.mainActivity).execute();
    }


    public void createUser(User user)
    {
        createUserAsyncTask = new CreateUserAsyncTask(UserDAO.this.applicationContext, user, UserDAO.this.mainActivity).execute();
    }


}
