package com.example.movie.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movie.Helpers.DatabaseHelper;
import com.example.movie.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private String inputEmail, inputPassword;
    private TextView tv_email, tv_changePassword;
    private ImageView profilePicture;
    private static final int PICK_IMAGE = 1;
    final int REQUEST_CODE_GALLERY = 999;
    private Button addButton;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private Boolean updatePsw, setPicture;
    private byte [] img;
    private Bitmap imageToBitmap;
    private String resultForImage;

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
        profilePicture.setImageResource(R.drawable.profilepic);
        addButton = findViewById(R.id.buttonAdd);
        tv_email.setText(inputEmail);

        tv_changePassword.setOnClickListener(this);
        profilePicture.setOnClickListener(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   setPicture =  databaseHelper.updateDatabase(inputEmail,imageViewToByte(profilePicture));
                   Toast.makeText(ProfileActivity.this, "added successfully", Toast.LENGTH_SHORT).show();

            }
        });

        img = databaseHelper.getStoredImage(inputEmail);
        Log.e("a kep", img.toString());
        if(img != null)
        {
            imageToBitmap = getImage(img);
            profilePicture.setImageBitmap(imageToBitmap);

        }

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

                        ActivityCompat.requestPermissions(
                                ProfileActivity.this,
                                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_GALLERY
                        );

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if(requestCode == REQUEST_CODE_GALLERY)
       {
           if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
           {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
           }
           else{
               Toast.makeText(ProfileActivity.this, "you dont have permission", Toast.LENGTH_SHORT).show();
           }
           return;
       }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                profilePicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
