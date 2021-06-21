package com.example.healthinspector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SignupActivity extends AppCompatActivity {

    EditText myfullname, myemailid, mypassword;
    ImageButton myregisterbutton;
    TextView myTextView;
    ProgressBar myprogressbar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        myfullname = (EditText) findViewById(R.id.fullname);
        myemailid  = (EditText) findViewById(R.id.emailid);
        mypassword = (EditText) findViewById(R.id.password);
        myTextView = (TextView) findViewById(R.id.textView);
        myregisterbutton = (ImageButton) findViewById(R.id.registerbutton);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        myprogressbar = findViewById(R.id.progressbar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
            finish();
        }

        myregisterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = myemailid.getText().toString().trim();
                String password = mypassword.getText().toString().trim();
                String fullname = myfullname.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(TextUtils.isEmpty(fullname)){
                    myfullname.setError("Full name is required.");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    myemailid.setError("Email is required.");
                    return;
                }

                if (!email.matches(emailPattern)){
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
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

                // registering the user in firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "Signup successful.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Full Name", fullname);
                            user.put("Email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: User Profile is created for " + userID);
                                }
                            });
                            documentReference.set(user).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
                        }
                        else{
                            Toast.makeText(SignupActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            myprogressbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}