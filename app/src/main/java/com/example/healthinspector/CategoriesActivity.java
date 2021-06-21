package com.example.healthinspector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class CategoriesActivity extends AppCompatActivity {

    ImageButton mybathandbodycare, myfacecare, myhaircare, myoralcare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        mybathandbodycare = (ImageButton) findViewById(R.id.bathandbodycare);
        myfacecare = (ImageButton) findViewById(R.id.facecare);
        myhaircare = (ImageButton) findViewById(R.id.haircare);
        myoralcare = (ImageButton) findViewById(R.id.oralcare);

        mybathandbodycare.setOnClickListener(view -> {
            Intent intent = new Intent(CategoriesActivity.this, BathAndBodyCareActivity.class);
            startActivity(intent);
        });

        myfacecare.setOnClickListener(view -> {
            Intent intent = new Intent(CategoriesActivity.this, FaceCareActivity.class);
            startActivity(intent);
        });

        myhaircare.setOnClickListener(view -> {
            Intent intent = new Intent(CategoriesActivity.this, HairCareActivity.class);
            startActivity(intent);
        });

        myoralcare.setOnClickListener(view -> {
            Intent intent = new Intent(CategoriesActivity.this, OralCareActivity.class);
            startActivity(intent);
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}