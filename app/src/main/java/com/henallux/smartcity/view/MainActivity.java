package com.henallux.smartcity.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.henallux.smartcity.R;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.exception.BadLoginPasswordException;
import com.henallux.smartcity.task.LoginAsyncTask;
import com.henallux.smartcity.utils.Constantes;
import com.henallux.smartcity.utils.Utils;
import com.henallux.smartcity.dataAccess.UserDAO;

public class MainActivity extends AppCompatActivity {

    private Button inscriptionButton;
    private Button connectionButton;
    private EditText loginInput;
    private EditText passwordInput;
    private Button withoutConnectionButton;
    private Application application;
    private LoginAsyncTask loginAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        application = (Application)getApplicationContext();

        inscriptionButton = findViewById(R.id.InscriptionButton);

        connectionButton = findViewById(R.id.ConnectionButton);
        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                application.setConnected(true);
                Intent intent = new Intent(MainActivity.this,InfoPersos.class);
                startActivity(intent);
            }
        });
        loginInput = findViewById(R.id.loginInput);
        passwordInput = findViewById(R.id.motDePasseInput);
        connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForms())
                {
                    application.setConnected(true);
                    loginAsyncTask = new LoginAsyncTask(loginInput.getText().toString(), passwordInput.getText().toString(), MainActivity.this);
                    loginAsyncTask.execute();
                }
            }
        });

        withoutConnectionButton = findViewById(R.id.withoutConnectionButton);
        withoutConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                application.setConnected(false);
                String userLambda =Constantes.DEFAULT_USER_NAME;
                String passwordLambda=Constantes.DEFAULT_USER_PASSWORD;
                loginAsyncTask = new LoginAsyncTask(userLambda, passwordLambda, MainActivity.this);
                loginAsyncTask.execute();
            }
        });
    }

    public boolean checkForms() {
        if(Utils.isEmpty(loginInput)){
            Toast.makeText(this, Constantes.EMPTY_LOGIN, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Utils.isEmpty(passwordInput)){
            Toast.makeText(this, Constantes.EMPTY_PASSWORD, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loginAsyncTask != null){
            loginAsyncTask.cancel(true);
        }
    }
}
