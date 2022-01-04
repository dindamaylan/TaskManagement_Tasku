package com.dindamaylan.tasku.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.dindamaylan.tasku.R;
import com.dindamaylan.tasku.databinding.ActivitySignInBinding;
import com.dindamaylan.tasku.repo.remote.UserRepo;
import com.dindamaylan.tasku.ui.home.HomeAct;
import com.dindamaylan.tasku.utils.Helpers;
import com.google.firebase.database.DatabaseReference;

public class SignInAct extends AppCompatActivity {

    private ProgressDialog loader;
    DatabaseReference reference;

    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnMasuk.setOnClickListener(v -> {
            loginUser(binding.etUsername.getText().toString(),
                    new Helpers().getHashPassword(binding.etPassword.getText().toString()));
        });

        binding.btnDaftar.setOnClickListener(v -> {
            Intent toSignUp = new Intent(SignInAct.this, SignUpAct.class);
            startActivity(toSignUp);
            finish();
        });

    }

    private void loginUser(String username, String password){
        new UserRepo().loginUser(this, username, password, (isSuccess, message) ->{
            if (isSuccess){
                Toast.makeText(this, "hello"+message+" !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeAct.class));
                finish();
            }else {
                Toast.makeText(this, "hello"+message+" !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}