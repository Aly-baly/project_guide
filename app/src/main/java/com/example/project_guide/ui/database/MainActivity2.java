package com.example.project_guide.ui.database;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class MainActivity2 extends AppCompatActivity {

    private final static String TAG = "Login Activity";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;
    EditText LoginEmail;
    EditText LoginPassword;
    TextView registerHere;
    Button loginBtn;
    // FrameLayout buttons;

    FirebaseUser user;

    /**
     * AlertDialog
     */
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Error")
                .setMessage("Incorrect login or password")
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .create();

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void VerifiedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Error")
                .setMessage("Confirm the account")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity2.this, "Verification email has been sent ", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                            }
                        });
                    }
                })
                .create();

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        //  buttons = findViewById(R.id.buttons);
        LoginEmail = (EditText) findViewById(R.id.emailLog);
        LoginPassword = (EditText) findViewById(R.id.pasLog);
        registerHere = (TextView) findViewById(R.id.toReg);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        user = mAuth.getCurrentUser();
        loginBtn.setOnClickListener(view -> {
            loginUser();
        });
        registerHere.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity2.this, RegistrationPage.class));
        });

    }

    private void loginUser() {
        String email = LoginEmail.getText().toString().trim();
        String password = LoginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            LoginEmail.setError("Email cannot be empty");
            LoginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            LoginPassword.setError("Password cannot be empty");
            LoginPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (!user.isEmailVerified()) {
                            VerifiedDialog();
                        } else {
                            startActivity(new Intent(MainActivity2.this, MainActivity.class));
                            Toast.makeText(MainActivity2.this, "Login Successful", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        showAlertDialog();
                        Toast.makeText(MainActivity2.this, "Login Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

}