package com.example.movie.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movie.Helpers.AndroidDatabaseManager;
import com.example.movie.Helpers.DatabaseHelper;
import com.example.movie.R;

public class LoginActivity extends AppCompatActivity  {

    private DatabaseHelper databaseHelper;
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private String inputEmail, inputPassword;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);
        editTextEmail = findViewById(R.id.editTextEmail2);
        editTextPassword = findViewById(R.id.editTextPassword2);
        buttonLogin = findViewById(R.id.buttonLogin2);
        tv = findViewById(R.id.textViewDatabase);

        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(LoginActivity.this, AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEmail = editTextEmail.getText().toString();
                inputPassword = editTextPassword.getText().toString();
                boolean checkEmailAndPassword = databaseHelper.validateEmailAndPassword(inputEmail,inputPassword);
                if(checkEmailAndPassword == true)
                {
                    Toast.makeText(getApplicationContext(),getString(R.string.successfulLog), Toast.LENGTH_SHORT).show();
                    Intent goToMoviesPage = new Intent(LoginActivity.this, MoviesActivity.class);
                    goToMoviesPage.putExtra("EmailOfUser",inputEmail);
                    goToMoviesPage.putExtra("PasswordOfUser",inputPassword);
                    startActivity(goToMoviesPage);
                }
                else{
                    Toast.makeText(getApplicationContext(),getString(R.string.invalidEmailorPassword), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
