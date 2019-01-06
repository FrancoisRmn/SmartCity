package com.henallux.smartcity.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                    try{
                        application.setConnected(true);
                        new LoginAsyncTask(loginInput.getText().toString(), passwordInput.getText().toString(), MainActivity.this).execute();
                    }
                    catch (Exception e){
                        System.out.println("Exception" + e);
                    }
                }
            }
        });

        withoutConnectionButton = findViewById(R.id.withoutConnectionButton);
        withoutConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        try{
                            application.setConnected(false);
                            String userLambda =Constantes.DEFAULT_USER_NAME;
                            String passwordLambda=Constantes.DEFAULT_USER_PASSWORD;
                            new LoginAsyncTask(userLambda, passwordLambda, MainActivity.this).execute();
                        }
                        catch (Exception e){
                            System.out.println("Exception" + e);
                        }
            }
        });
    }




    public boolean checkForms() {
        boolean isValid = true;
        if(Utils.isEmpty(loginInput)){
            Toast.makeText(this, "Login vide !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Utils.isEmpty(passwordInput)){
            Toast.makeText(this, "Vous devez rentez un mot de passe !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return isValid;
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1){
            switch (resultCode){
                case 1 : Toast.makeText(MainActivity.this, "Result code = 1", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
