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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.UserDAO;
import com.henallux.smartcity.exception.CannotRetreiveUserIdException;
import com.henallux.smartcity.model.Payload;
import com.henallux.smartcity.utils.Constantes;
import com.henallux.smartcity.utils.JWTUtils;

import org.json.JSONObject;

import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class SettingsFragment extends Fragment {
    private Button deconnectionbutton;
    private Application applicationContext;
    private Button deleteAccountButton;
    private UserDAO userDAO;
    private Switch switchNotifications;
    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        deconnectionbutton = v.findViewById(R.id.deconnectionbutton);
        deconnectionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applicationContext = (Application) getActivity().getApplicationContext();
                if (applicationContext.isConnected()) {
                    applicationContext.setToken("");
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
                                    int idUser =0;
                                    Payload payloadModel;
                                    if(payload.contains("uid")){
                                        JSONObject jsonPayload = new JSONObject(payload);
                                        payloadModel = new Payload(Integer.parseInt(jsonPayload.getString("uid")));
                                        idUser = payloadModel.getUid();
                                    }
                                    else{
                                        throw new CannotRetreiveUserIdException(Constantes.ERROR_MESSAGE_USERID);
                                    }
                                    SettingsFragment.this.userDAO = new UserDAO(getActivity());
                                    userDAO.deleteUser(idUser);
                                    Toast.makeText(getActivity(), Constantes.MESSAGE_ADD_USER, Toast.LENGTH_SHORT).show();
                                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                                }
                                catch(CannotRetreiveUserIdException e){
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e){
                                    System.out.print(e.getMessage());
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
                builder.setMessage("Cette action est irréversible, voulez-vous vraiment supprimer votre compte ?")
                        .setPositiveButton("Oui", dialogClickListener)
                        .setNegativeButton("Non", dialogClickListener).show();
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putBoolean(Constantes.PREFERENCES_NOTIFICATIONS, true);
        switchNotifications = v.findViewById(R.id.switch1);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        /*if(sharedPreferences.getBoolean(Constantes.PREFERENCES_NOTIFICATIONS, false)){
            switchNotifications.setChecked(true);
        }*/
        FirebaseMessaging.getInstance().unsubscribeFromTopic("Commerces");
        Map<String, ?> mapPreferences = sharedPreferences.getAll();
        for(String key: mapPreferences.keySet())
        {
            switchNotifications.setChecked(true);
            break;
        }
        switchNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchNotifications.isChecked()){
                    Toast.makeText(getActivity(), Constantes.ACTIVATED_NOTIFICATIONS , Toast.LENGTH_SHORT).show();
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

                    //on enregistre dans les préférences l'abonnement aux notifications
                }
                else{
                    Toast.makeText(getActivity(), Constantes.DISABLED_NOTIFICATIONS, Toast.LENGTH_SHORT).show();
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
        });
        return v;
    }
}
