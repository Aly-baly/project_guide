package com.example.project_guide.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_guide.MainActivity;
import com.example.project_guide.R;
import com.example.project_guide.databinding.FragmentProfileBinding;
import com.example.project_guide.ui.database.MainActivity2;
import com.example.project_guide.ui.database.RegistrationPage;
import com.example.project_guide.ui.topicItem.TopicActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private final static String TAG = "Login Activity";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;
    EditText LoginEmail;
    EditText LoginPassword;
    TextView registerHere;
    Button loginBtn;
    // FrameLayout buttons;

    FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_profile);
        //  buttons = findViewById(R.id.buttons);
        LoginEmail = root.findViewById(R.id.recyclerView);
        LoginPassword = root.findViewById(R.id.pasLog);
        registerHere = root.findViewById(R.id.toReg);
        loginBtn = root.findViewById(R.id.loginBtn);
        user = mAuth.getCurrentUser();
        loginBtn.setOnClickListener(view -> {
            loginUser();
        });
        registerHere.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), RegistrationPage.class));
        });


//        final TextView textView = binding.signIn;
//        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                                Toast.makeText(getActivity(), "Verification email has been sent ", Toast.LENGTH_LONG).show();

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
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    private void loginUser() {
        String email = LoginEmail.getText().toString();
        String password = LoginPassword.getText().toString();

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
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        showAlertDialog();
                        Toast.makeText(getActivity(), "Login Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

}
