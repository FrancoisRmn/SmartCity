package com.henallux.smartcity.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.henallux.smartcity.R;

public class InfoPersos extends AppCompatActivity {
private Button validateButton;
private EditText nameInput;
private EditText firstNameInput;
private EditText passWordInput;
private EditText confirmPassWordInput;
private EditText mailInput;
private EditText phoneInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_perso);
        nameInput = (EditText) findViewById(R.id.NameInput);
        firstNameInput = (EditText) findViewById(R.id.FirstNameInput);
        mailInput = (EditText) findViewById(R.id.MailInput);
        passWordInput = (EditText) findViewById(R.id.PasswordInput);
        confirmPassWordInput = (EditText) findViewById(R.id.ConfirmPasswordInput);
        phoneInput = (EditText) findViewById(R.id.PhoneInput);
        validateButton = (Button)findViewById(R.id.ValidationInscriptionButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForms())
                {
                    startActivity(new Intent(InfoPersos.this, Preference.class));
                }
            }
        });
    }
    //TODO Verif formulaire
    public boolean checkForms()
    {
        boolean isValid = true;
        if(isEmpty(nameInput)){
            Toast.makeText(this, "Vous n'avez pas entré de nom", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(isEmpty(firstNameInput)){
            Toast.makeText(this, "Vous n'avez pas entré de prénom", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(isEmpty(mailInput)){
            Toast.makeText(this, "Vous n'avez pas insérer d'email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(isEmpty(passWordInput)){
            Toast.makeText(this, "Vous n'avez pas entré de mot de passe", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(isEmpty(confirmPassWordInput)){
            Toast.makeText(this, "Vous n'avez pas entré de mot de passe", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!confirmPassWordInput.getText().toString().equals(passWordInput.getText().toString())){
            Toast.makeText(this, "Vous devez insérer deux fois le même mot de passe !", Toast.LENGTH_SHORT).show();
            return false;
        }


        if(!isEmpty(phoneInput)){
            try{
                Integer.parseInt(phoneInput.getText().toString().replace("\\s", ""));
                if(phoneInput.getText().length()>10){
                    Toast.makeText(this, "Vous n'avez pas insérer de numéro de téléphone valide", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            catch (NumberFormatException e){
                Toast.makeText(this, "Le numéro de téléphone ne peut contenir que des chiffres", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else{
            Toast.makeText(this, "Vous n'avez pas entré de numéro de téléphone", Toast.LENGTH_SHORT).show();
            return false;
        }
        return isValid;
    }

    private boolean isEmpty(EditText editText)
    {
        return editText.getText().toString().trim().length() == 0;
    }
}
