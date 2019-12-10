package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister, buttonLogin;
    private String inputEmail, inputPassword, inputConfirmPassword;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLoginPage = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goToLoginPage);

            }
        });

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        inputEmail = editTextEmail.getText().toString();
        inputPassword = editTextPassword.getText().toString();
        inputConfirmPassword = editTextConfirmPassword.getText().toString();

        if(inputEmail.equals("") || inputPassword.equals("") || inputConfirmPassword.equals(""))
        {
            Toast.makeText(getApplicationContext(), getString(R.string.missingInformation), Toast.LENGTH_SHORT).show();
        }
        else{
            if(inputPassword.equals(inputConfirmPassword))
            {
                Boolean checkEmail = databaseHelper.checkEmail(inputEmail);
                if(checkEmail == true)
                {
                    Boolean insert = databaseHelper.insert(inputEmail, inputPassword);
                    if(insert == true){
                        Toast.makeText(getApplicationContext(), getString(R.string.successfulReg), Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), getString(R.string.existingEmail), Toast.LENGTH_SHORT).show();
                }
            }
            else{

                Toast.makeText(getApplicationContext(), getString(R.string.notMatchingPassword), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
