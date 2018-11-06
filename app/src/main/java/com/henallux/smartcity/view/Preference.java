package com.henallux.smartcity.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.henallux.smartcity.R;

public class Preference extends AppCompatActivity {
    private Button connectionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        connectionButton = findViewById(R.id.creationButton);
        connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Preference.this,BottomMenu.class);
                startActivity(intent);
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
