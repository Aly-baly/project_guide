package com.example.project_guide.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_guide.R;
import com.example.project_guide.databinding.FragmentPersonalAccountBinding;
import com.example.project_guide.databinding.FragmentProfileBinding;
import com.example.project_guide.ui.database.MainActivity2;
import com.example.project_guide.ui.database.RegistrationPage;

public class PersonalAccount extends Fragment {

    private FragmentPersonalAccountBinding binding;
    Button btnReg;
    Button btnLog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PersonalAccountViewModel personalAccountViewModel=
                new ViewModelProvider(this).get(PersonalAccountViewModel.class);

        binding = FragmentPersonalAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnReg = root.findViewById(R.id.btnReg);
        btnLog = root.findViewById(R.id.btnLog);

        btnReg.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), RegistrationPage.class));
        });

        btnLog.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), MainActivity2.class));
        });

        return root;
    }
}