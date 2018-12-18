package com.henallux.smartcity.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.henallux.smartcity.R;
import com.henallux.smartcity.Utils.Utils;
import com.henallux.smartcity.dataAccess.UserDAO;
import com.henallux.smartcity.model.User;

public class InfoPersos extends AppCompatActivity {
private Button validateButton;
private EditText nameInput;
private EditText firstNameInput;
private EditText passWordInput;
private EditText confirmPassWordInput;
private EditText mailInput;
private Button validationInscriptionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_perso);
        nameInput = (EditText) findViewById(R.id.NameInput);
        firstNameInput = (EditText) findViewById(R.id.FirstNameInput);
        mailInput = (EditText) findViewById(R.id.MailInput);
        passWordInput = (EditText) findViewById(R.id.PasswordInput);
        confirmPassWordInput = (EditText) findViewById(R.id.ConfirmPasswordInput);

        /*
        validateButton = (Button)findViewById(R.id.ValidationInscriptionButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForms())
                {
                    //on cree un user
                    String userName= nameInput.getText().toString() + firstNameInput.getText().toString();

                    User user = new User(userName, passWordInput.getText().toString(), mailInput.getText().toString());
                    Intent intent = new Intent(InfoPersos.this, Preference.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });
         */
        validationInscriptionButton = (Button) findViewById(R.id.validationInscriptionButton);
        validationInscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForms())
                {
                    //on cree un user
                    String userName= nameInput.getText().toString() + firstNameInput.getText().toString();

                    User user = new User(userName, passWordInput.getText().toString(), mailInput.getText().toString());
                    UserDAO userDAO = new UserDAO(getApplicationContext(), InfoPersos.this);
                    try{
                        userDAO.createUser(user);
                    }
                    catch (Exception e){
                        System.out.println("Exception" + e);
                        Toast.makeText(InfoPersos.this, "Erreur lors de la création de l'utilisateur", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    //TODO Verif formulaire
    public boolean checkForms()
    {
        boolean isValid = true;
        if(Utils.isEmpty(nameInput)){
            Toast.makeText(this, "Vous n'avez pas entré de nom", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Utils.isEmpty(firstNameInput)){
            Toast.makeText(this, "Vous n'avez pas entré de prénom", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Utils.isEmpty(mailInput)){
            Toast.makeText(this, "Vous n'avez pas insérer d'email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Utils.isEmpty(passWordInput)){
            Toast.makeText(this, "Vous n'avez pas entré de mot de passe", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passWordInput.getText().length() <  6){
            Toast.makeText(this, "Votre mot de passe doit contenir plus de 6 caractères !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Utils.isEmpty(confirmPassWordInput)){
            Toast.makeText(this, "Vous n'avez pas entré de mot de passe", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!confirmPassWordInput.getText().toString().equals(passWordInput.getText().toString())){
            Toast.makeText(this, "Vous devez insérer deux fois le même mot de passe !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return isValid;
    }
}
