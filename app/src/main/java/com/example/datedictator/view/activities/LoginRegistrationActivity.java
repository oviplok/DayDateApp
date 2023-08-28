package com.example.datedictator.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.datedictator.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegistrationActivity extends AppCompatActivity {
//    private static final String SHARED_PREF = "sharedPrefs";
//    private static final String EMAIL = "email",PASSWORD = "password";
    private Button mLogin, mRegistration;
    private ProgressBar mProgBar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);
        mLogin = findViewById(R.id.to_login_button);
        mRegistration = findViewById(R.id.to_reg_button);
        mProgBar = findViewById(R.id.loading_activity_reglog);

//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
//        String Email = sharedPreferences.getString(EMAIL,"");
//        String Password = sharedPreferences.getString(PASSWORD,"");
//
//        if(!Email.equals("") && !Password.equals("")){
//            //mapAct(Email,,income_place);
//        }
        //mProgBar.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LoginRegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                else {
                    Toast.makeText(LoginRegistrationActivity.this,
                            "Enter to your profile", Toast.LENGTH_SHORT).show();
                }
                //mProgBar.setVisibility(View.GONE);
            }
        };
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginRegistrationActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}