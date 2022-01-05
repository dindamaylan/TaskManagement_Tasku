package com.dindamaylan.tasku.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.dindamaylan.tasku.databinding.ActivityHomeBinding;
import com.dindamaylan.tasku.databinding.DialogLogoutBinding;
import com.dindamaylan.tasku.repo.local.LocalStore;
import com.dindamaylan.tasku.repo.remote.UserRepo;
import com.dindamaylan.tasku.ui.SignInAct;
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
            onBackPressed();
        });

        binding.avaUser.setOnClickListener(v -> {
            showLogoutDialog();
        });


        binding.scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            System.out.println("Scroll Y = " + scrollY + " scroll X = " + scrollX);
            if (scrollY < 50) {
                // scrolling down
                binding.avaUser.setVisibility(View.VISIBLE);
                binding.btnBack.setVisibility(View.VISIBLE);
            } else {
                // scrolling up
                binding.avaUser.setVisibility(View.GONE);
                binding.btnBack.setVisibility(View.GONE);
            }
        });
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        DialogLogoutBinding dialogBinding = DialogLogoutBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());

        Dialog dialog = builder.create();
        dialog.show();

        dialogBinding.btnNo.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialogBinding.btnYes.setOnClickListener(v -> {
            //logout
            new UserRepo().logout(this, (isSuccess, message) -> {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                if (isSuccess) {
                    startActivity(new Intent(this, SignInAct.class));
                    finish();
                }
            });
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
        binding.btnBack.setVisibility(View.VISIBLE);
        replaceFragment(MenuTaskFragment.getInstance(currentUserId, status));
    }

    @Override
    public void onBackPressed() {
        if (isInMenu) {
            replaceFragment(HomeFragment.newInstance(this, currentUserId));
            isInMenu = !isInMenu;
            binding.btnBack.setVisibility(View.GONE);
        } else super.onBackPressed();
    }
}
