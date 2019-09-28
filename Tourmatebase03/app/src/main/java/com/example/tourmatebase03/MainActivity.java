package com.example.tourmatebase03;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText emailET,passET;
    private Button loginbtn,registerbtn;
    private TextView statusTV;
    private UserPreference userPreference;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        emailET = findViewById( R.id.emailEditText );
        passET = findViewById( R.id.passwordEditText );
        loginbtn = findViewById( R.id.loginbtn );
        registerbtn = findViewById( R.id.registerbtn );
        statusTV = findViewById( R.id.statusTV );
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userPreference = new UserPreference(this);


        if(userPreference.getLoginStatus()){
            startActivity(new Intent(MainActivity.this,EventListActivity.class));
            finish();
        }

    }

    public void registerUser(View view) {

        String email = emailET.getText().toString();
        String password = passET.getText().toString();
        userPreference.setLoginStatus(true);
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user = firebaseAuth.getCurrentUser();
                            //binding.statusTV.setText("Logged in as "+user.getEmail());
                            startActivity(new Intent(MainActivity.this, EventListActivity.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                statusTV.setText(e.getLocalizedMessage());
            }
        });


    }

    public void loginUser(View view) {
        String email = emailET.getText().toString();
        String password = passET.getText().toString();
        userPreference.setLoginStatus(true);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user = firebaseAuth.getCurrentUser();
                            //binding.statusTV.setText("Logged in as "+user.getEmail());
                            startActivity(new Intent(MainActivity.this, EventListActivity.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                statusTV.setText(e.getLocalizedMessage());
            }
        });


    }
}
