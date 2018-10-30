package com.henallux.smartcity.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.henallux.smartcity.R;

public class InfoPersos extends AppCompatActivity {
private Button boutonValider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_perso);

        boutonValider = (Button)findViewById(R.id.ValidationInscriptionButton);
        boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoPersos.this, Preference.class));
            }
        });
    }
}
