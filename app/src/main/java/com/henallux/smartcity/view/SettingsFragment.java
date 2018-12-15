package com.henallux.smartcity.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.henallux.smartcity.ApplicationObject.Application;
import com.henallux.smartcity.R;

public class SettingsFragment extends Fragment {
    private Button deconnectionbutton;
    private Application applicationContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_settings, container, false);
        deconnectionbutton = v.findViewById(R.id.deconnectionbutton);
        deconnectionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applicationContext = (Application)getActivity().getApplicationContext();
                if(applicationContext.isConnected()){
                    applicationContext.setToken("");
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    Toast.makeText(getActivity(), "Vous êtes connectés !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Vous n'êtes pas connectés !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
}
