package com.henallux.smartcity.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.henallux.smartcity.R;
import com.henallux.smartcity.model.User;
import com.henallux.smartcity.task.CreateUserAsyncTask;
import com.henallux.smartcity.utils.Utils;

public class InfoPersos extends AppCompatActivity {
private EditText nameInput;
private EditText firstNameInput;
private EditText passWordInput;
private EditText confirmPassWordInput;
private EditText mailInput;
private Button validationInscriptionButton;
private CreateUserAsyncTask createUserAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_perso);
        nameInput = (EditText) findViewById(R.id.NameInput);
        firstNameInput = (EditText) findViewById(R.id.FirstNameInput);
        mailInput = (EditText) findViewById(R.id.MailInput);
        passWordInput = (EditText) findViewById(R.id.PasswordInput);
        confirmPassWordInput = (EditText) findViewById(R.id.ConfirmPasswordInput);
        validationInscriptionButton = (Button) findViewById(R.id.validationInscriptionButton);
        validationInscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForms())
                {
                    String userName= nameInput.getText().toString() + firstNameInput.getText().toString();
                    User user = new User(userName, passWordInput.getText().toString(), mailInput.getText().toString());
                    createUserAsyncTask = new CreateUserAsyncTask(user, InfoPersos.this);
                    createUserAsyncTask.execute();
                }

            }
        });
    }
    public boolean checkForms()
    {
        if(Utils.isEmpty(nameInput)){
            Toast.makeText(this, R.string.nameInputEmpty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Utils.isEmpty(firstNameInput)){
            Toast.makeText(this, R.string.firstNameEmpty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Utils.isEmpty(mailInput)){
            Toast.makeText(this, R.string.emailEmpty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!Utils.verificationEmail(mailInput.getText().toString())){
            Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Utils.isEmpty(passWordInput)){
            Toast.makeText(this, R.string.passwordEmpty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passWordInput.getText().length() <  6){
            Toast.makeText(this, R.string.passwordTooShort, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Utils.isEmpty(confirmPassWordInput)){
            Toast.makeText(this, R.string.confirmPasswordInput, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!confirmPassWordInput.getText().toString().equals(passWordInput.getText().toString())){
            Toast.makeText(this, R.string.NotSamePassword, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.createUserAsyncTask != null){
            this.createUserAsyncTask.cancel(true);
        }
    }
}
