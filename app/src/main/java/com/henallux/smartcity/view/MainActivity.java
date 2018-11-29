package com.henallux.smartcity.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import com.henallux.smartcity.R;
import com.henallux.smartcity.Utils.Utils;

public class MainActivity extends AppCompatActivity {

    private Button inscriptionButton;
    private Button connectionButton;
    private EditText loginInput;
    private EditText passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inscriptionButton = findViewById(R.id.InscriptionButton);

        connectionButton = findViewById(R.id.ConnectionButton);
        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Intent intent = new Intent(MainActivity.this,BottomMenu.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean checkForms() {
        boolean isValid = true;
        if(Utils.isEmpty(loginInput)){
            Toast.makeText(this, "Login incorrect !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Utils.isEmpty(loginInput)){
            Toast.makeText(this, "Mot de passe incorrect !", Toast.LENGTH_SHORT).show();
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
