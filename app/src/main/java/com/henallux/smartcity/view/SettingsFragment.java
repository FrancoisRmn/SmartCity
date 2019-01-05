package com.henallux.smartcity.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.UserDAO;
import com.henallux.smartcity.exception.CannotRetreiveUserIdException;
import com.henallux.smartcity.exception.ImpossibleToDeleteUserException;
import com.henallux.smartcity.model.Payload;
import com.henallux.smartcity.task.DeleteUserAsyncTask;
import com.henallux.smartcity.utils.JWTUtils;

import org.json.JSONObject;

public class SettingsFragment extends Fragment {
    private Button deconnectionbutton;
    private Application applicationContext;
    private Button deleteAccountButton;
    private UserDAO userDAO;

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
                    Toast.makeText(getActivity(), "Vous êtes connectés !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Vous n'êtes pas connectés !", Toast.LENGTH_SHORT).show();
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
                                        throw new CannotRetreiveUserIdException("Impossible de récupérer l'identidiant de l'utilisateur !");
                                    }
                                    SettingsFragment.this.userDAO = new UserDAO(getActivity());
                                    userDAO.deleteUser(idUser);
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
        return v;
    }
}
