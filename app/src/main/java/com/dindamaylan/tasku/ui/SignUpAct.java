package com.dindamaylan.tasku.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dindamaylan.tasku.data.UserData;
import com.dindamaylan.tasku.databinding.ActivitySignUpBinding;
import com.dindamaylan.tasku.repo.remote.UserRepo;
import com.dindamaylan.tasku.utils.Helpers;
import com.google.firebase.database.DatabaseReference;

public class SignUpAct extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSimpan.setOnClickListener(v -> {
            createNewUser();
        });

        binding.tvLogin.setOnClickListener(v -> {

        });
    }

    private void createNewUser(){
        UserData user = new UserData(
                binding.etName.getText().toString(),
                binding.etUsername.getText().toString(),
                new Helpers().getHashPassword(binding.etPassword.getText().toString()),
                binding.etBio.getText().toString(),
                new UserRepo().generateId()

        );

        new UserRepo().createNewUser(user, (isSuccess, message) -> {
            if (isSuccess) startActivity(new Intent(this, SignInAct.class));
            else Toast.makeText(this, "message : "+ message, Toast.LENGTH_SHORT).show();
        });
    }
}