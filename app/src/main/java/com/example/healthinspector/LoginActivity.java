package com.example.healthinspector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText myemailid, mypassword;
    ImageButton myLoginButton;
    ProgressBar myprogressbar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myemailid = (EditText) findViewById(R.id.emailid);
        mypassword = (EditText) findViewById(R.id.password);
        myLoginButton = (ImageButton) findViewById(R.id.loginbutton);

        fAuth = FirebaseAuth.getInstance();
        myprogressbar = findViewById(R.id.progressbar);

        myLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = myemailid.getText().toString().trim();
                String password = mypassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    myemailid.setError("Email is required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mypassword.setError("Password is required.");
                    return;
                }

                if(password.length() < 6){
                    mypassword.setError("Password must be >= 6 characters.");
                    return;
                }

                myprogressbar.setVisibility(View.VISIBLE);

                // authenticating the user to login

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            myprogressbar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });


    }
}