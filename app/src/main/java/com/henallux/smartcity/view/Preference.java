package com.henallux.smartcity.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.UserDAO;
import com.henallux.smartcity.model.User;

public class Preference extends AppCompatActivity {
    private Button connectionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final User user;
        Bundle bundle = this.getIntent().getExtras();
        user = (User)bundle.getSerializable("user");

        setContentView(R.layout.activity_preferences);

        connectionButton = findViewById(R.id.creationButton);
        connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO userDAO = new UserDAO(getApplicationContext(), Preference.this);
                try{
                    userDAO.createUser(user.getUserName(), user.getPassword());
                }
                catch (Exception e){
                    System.out.println("Exception" + e);
                    Toast.makeText(Preference.this, "Erreur lors de la création de l'utilisateur", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_bar:
                if (checked){
                    Toast.makeText(Preference.this, "Vous avez cliquez sur un checkbox !", Toast.LENGTH_SHORT).show();
                }
                else
                {

                }
                break;
            case R.id.checkbox_restaurant:
                if (checked){

                }
                else
                {

                }
                break;
        }
    }
}
