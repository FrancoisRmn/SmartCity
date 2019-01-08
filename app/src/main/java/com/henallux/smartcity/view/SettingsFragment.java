package com.henallux.smartcity.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.UserDAO;
import com.henallux.smartcity.exception.CannotRetreiveUserIdException;
import com.henallux.smartcity.model.Payload;
import com.henallux.smartcity.task.DeleteUserAsyncTask;
import com.henallux.smartcity.utils.Constantes;
import com.henallux.smartcity.utils.JWTUtils;

import org.json.JSONObject;

import java.util.Map;

public class SettingsFragment extends Fragment {
    private Button deconnectionButton;
    private Application application;
    private Button deleteAccountButton;
    private UserDAO userDAO;
    private Switch switchNotifications;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (Application)getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        deconnectionButton = v.findViewById(R.id.deconnectionbutton);
        deconnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (application.isConnected()) {
                    application.setToken("");
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    Toast.makeText(getActivity(), Constantes.MESSAGE_DISCONNECTED, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), Constantes.MESSAGE_NOT_DISCONNECTED , Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteAccountButton = v.findViewById(R.id.buttonDeleteAccount);
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                try {
                                    Application application = (Application)getActivity().getApplicationContext();
                                    String payload = JWTUtils.decoded(application.getToken());
                                    int idUser;
                                    Payload payloadModel;
                                    if(payload.contains("uid")){
                                        JSONObject jsonPayload = new JSONObject(payload);
                                        payloadModel = new Payload(Integer.parseInt(jsonPayload.getString("uid")));
                                        idUser = payloadModel.getUid();
                                    }
                                    else{
                                        throw new CannotRetreiveUserIdException(Constantes.ERROR_MESSAGE_USERID);
                                    }
                                    new DeleteUserAsyncTask(getActivity(), idUser).execute();
                                    Toast.makeText(getActivity(), Constantes.MESSAGE_ADD_USER, Toast.LENGTH_SHORT).show();
                                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                                }
                                catch(CannotRetreiveUserIdException e){
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e){
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();                                }
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
                builder.setMessage(Constantes.ULTIMATUM_DELETE_USER)
                        .setPositiveButton(Constantes.POSITIVE_MESSAGE, dialogClickListener)
                        .setNegativeButton(Constantes.NEGATIVE_MESSAGE, dialogClickListener).show();
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        switchNotifications = v.findViewById(R.id.switch1);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if(application.isActivateNotifications()) {
            switchNotifications.setChecked(true);
            subscribeToAllCommerces();
        }
        switchNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchNotifications.isChecked()){
                    Toast.makeText(getActivity(), Constantes.ACTIVATED_NOTIFICATIONS , Toast.LENGTH_SHORT).show();
                    subscribeToAllCommerces();
                    application.setActivateNotifications(true);
                }
                else{
                    Toast.makeText(getActivity(), Constantes.DISABLED_NOTIFICATIONS, Toast.LENGTH_SHORT).show();
                    unsubscribeToAllCommerces();
                    application.setActivateNotifications(false);
                }
                editor.commit();
            }
        });
        return v;
    }

    public void subscribeToAllCommerces()
    {
        Map<String, ?> mapPreferences = sharedPreferences.getAll();
        for(String key: mapPreferences.keySet())
        {
            FirebaseMessaging.getInstance().subscribeToTopic(key)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getActivity(), Constantes.ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

    public void unsubscribeToAllCommerces()
    {
        Map<String, ?> mapPreferences = sharedPreferences.getAll();
        for(String key: mapPreferences.keySet())
        {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(key)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getActivity(), Constantes.ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }
}
