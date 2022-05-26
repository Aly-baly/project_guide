package com.example.project_guide.ui.database;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_guide.MainActivity;
import com.example.project_guide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationPage extends AppCompatActivity {

    private final static String TAG = "Registration Activity";
    EditText RegEmail;
    EditText RegPassword;
    TextView LoginHere;
    Button Register;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration);
        RegEmail = findViewById(R.id.emailReg);
        RegPassword = findViewById(R.id.pasReg);
        LoginHere = findViewById(R.id.toLog);
        Register = findViewById(R.id.regBtn);

        mAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(view -> {
            createUser();
        });

        LoginHere.setOnClickListener(view -> {
            startActivity(new Intent(RegistrationPage.this, MainActivity2.class));
        });
    }

    private void createUser(){
        String email = RegEmail.getText().toString();
        String password = RegPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            RegEmail.setError("Email cannot be empty");
            RegEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            RegPassword.setError("Password cannot be empty");
            RegPassword.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        // send verification link
                        FirebaseUser user = mAuth.getCurrentUser();
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RegistrationPage.this, "Verification email has been sent ", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"onFailure: Email not sent "+ e.getMessage());
                            }
                        });
                        Toast.makeText(RegistrationPage.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationPage.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(RegistrationPage.this, "Registration Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

}
