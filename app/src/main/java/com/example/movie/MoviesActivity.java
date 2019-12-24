package com.example.movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.action_home:
                        Toast.makeText(MoviesActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent (MoviesActivity.this, ProbaActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.action_details:
                        Toast.makeText(MoviesActivity.this, "Details clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_profile:
                        Toast.makeText(MoviesActivity.this, "profile clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_favorites:
                        Toast.makeText(MoviesActivity.this, "favorites clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
}
