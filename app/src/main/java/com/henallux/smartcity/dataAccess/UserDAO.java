package com.henallux.smartcity.dataAccess;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.henallux.smartcity.model.User;
import com.henallux.smartcity.task.CreateUserAsyncTask;
import com.henallux.smartcity.task.DeleteUserAsyncTask;
import com.henallux.smartcity.task.LoginAsyncTask;

public class UserDAO {
    private AsyncTask loginUserAsyncTask;
    private AsyncTask createUserAsyncTask;
    private Activity mainActivity;

    public UserDAO(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void login(String username, String password){
        Log.i("Login","Début de login dans UserDAO");
        loginUserAsyncTask = new LoginAsyncTask(this.mainActivity.getApplication().getApplicationContext(), username, password, UserDAO.this.mainActivity).execute();
    }



    public void loginWithoutConnection() {
        //TODO déplacer ça dans les constantes et pas utiliser janedoe vu que c'est un admin
        String userLambda ="janedoe";
        String passwordLambda="123";
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
