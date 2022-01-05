package com.dindamaylan.tasku.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dindamaylan.tasku.R;
import com.dindamaylan.tasku.databinding.ActivityRePasswordBinding;
import com.dindamaylan.tasku.utils.Helpers;

public class RePasswordAct extends AppCompatActivity {
    private ActivityRePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_password);
        binding = ActivityRePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSimpan.setOnClickListener(v -> {
            rePassword(binding.etUsername.getText().toString(),
                    new Helpers().getHashPassword(binding.etNewPassword.getText().toString()));
        });
    }

    private void rePassword(String username, String password) {

    }
}