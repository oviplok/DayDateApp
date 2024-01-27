package com.example.datedictator.view.activities.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datedictator.R;
import com.example.datedictator.view.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
//    private static final String SHARED_PREF = "sharedPrefs";
//    private static final String EMAIL = "email",PASSWORD = "password";
    private EditText mEmail, mPassword,mName;
    private RadioGroup mGender;
    private Button mRegButton;
    private ProgressBar mProgBar;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.fragment_registration);
        mGender = findViewById(R.id.radioGroup);
        mName= findViewById(R.id.name_registration);
        mEmail = findViewById(R.id.email_registration);
        mPassword = findViewById(R.id.password_registration);
        mRegButton = findViewById(R.id.registration_button);
        mProgBar = findViewById(R.id.loading_registration);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mProgBar.setVisibility(View.VISIBLE);
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                mProgBar.setVisibility(View.GONE);
            }
        };

        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGender = mGender.getCheckedRadioButtonId();
                final RadioButton radioButton =findViewById(selectedGender);
                if (!radioButton.isChecked()){
                    Toast.makeText(RegistrationActivity.this,
                            "Choose your gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mName.getText().toString())) {
                    Toast.makeText(RegistrationActivity.this,
                            "Write your name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mEmail.getText().toString())) {
                    Toast.makeText(RegistrationActivity.this,
                            "Write your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Проверка почты на сколько это почта
                if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()){
                    Toast.makeText(RegistrationActivity.this,
                            "Doesn't looks like email", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Проверка пароля на парольность
                if (mPassword.getText().toString().length() < 6 || TextUtils.isEmpty(mEmail.getText().toString())) {
                    Toast.makeText(RegistrationActivity.this,
                            "Password to easy(", Toast.LENGTH_SHORT).show();
                    return;
                }

                StartReg(mEmail.getText().toString(),
                        mPassword.getText().toString(),mName.getText().toString(),radioButton.getText().toString());
            }
        });

    }

    private void StartReg(String email, String password, String name, String gender) {
        mProgBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){

                    Toast.makeText(RegistrationActivity.this, email+" "+password, Toast.LENGTH_SHORT).show();

                }
                else {
                    String userId = mAuth.getCurrentUser().getUid();
                    DatabaseReference currentUserDb= FirebaseDatabase.getInstance()
                            .getReference().child("Users").child(gender).child(userId);
                    currentUserDb.child("name").setValue(name);
                    currentUserDb.child("Connections").child("Right").child("Uid").setValue(true);
                    currentUserDb.child("Connections").child("Left").child("Uid").setValue(true);
                    currentUserDb.child("Connections").child("Left").child("Uid").setValue(true);
                    currentUserDb.child("profileImageUrl\n").setValue("https://firebasestorage.googleapis.com/v0/b/date-dict-app.appspot.com/o/profileImages%2FXNl7aO-huvk.jpg?alt=media&token=7ca404b9-2b9c-4629-ba59-29d83d598fff");
                }
                mProgBar.setVisibility(View.GONE);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}