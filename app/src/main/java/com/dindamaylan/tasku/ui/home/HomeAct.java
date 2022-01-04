package com.dindamaylan.tasku.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.dindamaylan.tasku.databinding.ActivityHomeBinding;
import com.dindamaylan.tasku.repo.local.LocalStore;
import com.dindamaylan.tasku.repo.remote.UserRepo;
import com.dindamaylan.tasku.ui.TaskAddAct;

public class HomeAct extends AppCompatActivity implements TaskMenuAdapter.MenuTaskListener {

    private ActivityHomeBinding binding;
    private String currentUserId;
    private boolean isInMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LocalStore.getInstance(this).getUserId((isSuccess, user_id) -> {
            if (isSuccess) {
                currentUserId = user_id;
                getDataUser(user_id);
                getSupportFragmentManager().beginTransaction()
                        .replace(binding.fragmentContainer.getId(), HomeFragment.newInstance(this, user_id))
                        .commit();
            }
        });

        binding.btnAddTask.setOnClickListener(v -> {
            startActivity(new Intent(this, TaskAddAct.class));
        });

        binding.btnBack.setOnClickListener(v -> {
            binding.btnBack.setVisibility(View.GONE);
            binding.nameAndBio.setVisibility(View.VISIBLE);
            replaceFragment(HomeFragment.newInstance(this, currentUserId));
        });
    }

    private void getDataUser(String user_id) {
        new UserRepo().getUserId(user_id, ((isSuccess, user) -> {
            if (isSuccess) {
                binding.tvName.setText("Hi, " + user.name);
                binding.tvBio.setText(user.bio);
            }
        }));
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(binding.fragmentContainer.getId(), fragment)
                .commit();
    }

    @Override
    public void onMenuTaskClick(String status) {
        isInMenu = true;
        replaceFragment(MenuTaskFragment.getInstance(currentUserId, status));
    }

    @Override
    public void onBackPressed() {
        if (isInMenu) {
            replaceFragment(HomeFragment.newInstance(this, currentUserId));
            isInMenu = !isInMenu;
        } else super.onBackPressed();
    }
}
