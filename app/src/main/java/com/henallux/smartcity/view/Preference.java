package com.henallux.smartcity.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.henallux.smartcity.R;

public class Preference extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
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
