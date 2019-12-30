package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private String inputEmail, inputPassword;
    private TextView tv_email, tv_changePassword;
    private ImageView profilePicture;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private Boolean updatePsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle extras = getIntent().getExtras();
        inputEmail = extras.getString("EmailOfUser");
        inputPassword = extras.getString("PasswordOfUser");

        tv_email = findViewById(R.id.textViewName);
        tv_changePassword = findViewById(R.id.textViewChangePassword);
        profilePicture = findViewById(R.id.profilePic);
        tv_email.setText(inputEmail);

        tv_changePassword.setOnClickListener(this);
        profilePicture.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == tv_changePassword)
        {
            showChangePasswordDialog();
        }

        if(v == profilePicture)
        {
            showUploadImageDialog();
        }

    }

    public void showChangePasswordDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Change password");

        final Switch sw = new Switch(this);
        alertDialog.setView(sw);

        final EditText newPasswordInput = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        newPasswordInput.setLayoutParams(layoutParams);


        alertDialog.setView(newPasswordInput);
        alertDialog.setPositiveButton("CHANGE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newPassword = newPasswordInput.getText().toString();
                if(!TextUtils.isEmpty(newPassword)) {
                    updatePsw = databaseHelper.updatePassword(newPassword,inputEmail,inputPassword);
                    Toast.makeText(ProfileActivity.this, "Password successfully changed!", Toast.LENGTH_SHORT).show();

                }
            }

        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alertDialog.show();
    }

    public void showUploadImageDialog() {
        CharSequence[] items;

        items = new String[]{"Upload image"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ProfileActivity.this, "changed!", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
