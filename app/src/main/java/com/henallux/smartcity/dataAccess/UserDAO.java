package com.henallux.smartcity.dataAccess;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.henallux.smartcity.exception.BadLoginPasswordException;
import com.henallux.smartcity.model.User;
import com.henallux.smartcity.task.CreateUserAsyncTask;
import com.henallux.smartcity.task.DeleteUserAsyncTask;
import com.henallux.smartcity.task.LoginAsyncTask;
import com.henallux.smartcity.utils.Constantes;

public class UserDAO {
    private AsyncTask loginUserAsyncTask;
    private AsyncTask createUserAsyncTask;
    private Activity mainActivity;
    private Application application;

    public UserDAO(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void login(String username, String password) throws BadLoginPasswordException {
        Log.i("Login","Début de login dans UserDAO");
        loginUserAsyncTask = new LoginAsyncTask(this.mainActivity.getApplication().getApplicationContext(), username, password, UserDAO.this.mainActivity).execute();
    }



    public void loginWithoutConnection() {
        String userLambda =Constantes.DEFAULT_USER_NAME;
        String passwordLambda=Constantes.DEFAULT_USER_PASSWORD;
        application = (Application)mainActivity.getApplication().getApplicationContext();

        loginUserAsyncTask = new LoginAsyncTask(this.mainActivity.getApplication().getApplicationContext(), userLambda, passwordLambda, UserDAO.this.mainActivity).execute();
    }


    public void createUser(User user)
    {
        createUserAsyncTask = new CreateUserAsyncTask(UserDAO.this.mainActivity.getApplication().getApplicationContext(), user, UserDAO.this.mainActivity).execute();
    }

    public void deleteUser(int idUser)
    {
        new DeleteUserAsyncTask(mainActivity, idUser).execute();
    }
}
