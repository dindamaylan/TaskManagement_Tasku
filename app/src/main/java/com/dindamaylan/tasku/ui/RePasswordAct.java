package com.dindamaylan.tasku.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dindamaylan.tasku.R;
import com.dindamaylan.tasku.databinding.ActivityRePasswordBinding;
import com.dindamaylan.tasku.repo.remote.UserRepo;

public class RePasswordAct extends AppCompatActivity {
    private ActivityRePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_password);
        binding = ActivityRePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.layoutSuccess.btnLogin.setOnClickListener(v -> {

        });

        binding.btnSimpan.setOnClickListener(v -> {
            String passwordNew = binding.etNewPassword.getText().toString();
            String passwordConfirm = binding.etRePassword.getText().toString();
            String username = binding.etUsername.getText().toString();

            if (passwordNew.equals(passwordConfirm)) {
                //checking
                new UserRepo().loginUser(this, username, "", (isSuccess, message) -> {
                    if (message.equals("Password Salah")) {
                        new UserRepo().updatePassword(username, passwordNew, (isSuccessUpdate, message1) -> {
                            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                            if (isSuccessUpdate) {
                                binding.layoutforgetPassord.setVisibility(View.GONE);
                                binding.layoutSuccess.contentRoot.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            } else {
                Toast.makeText(this, "Konfirmasi password Anda tidak sesuai", Toast.LENGTH_SHORT).show();
            }
        });
    }
}