package com.dindamaylan.tasku.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.dindamaylan.tasku.R;
import com.dindamaylan.tasku.data.StatusTask;
import com.dindamaylan.tasku.data.TaskData;
import com.dindamaylan.tasku.databinding.FragmentMenuTaskBinding;
import com.dindamaylan.tasku.repo.remote.TaskRepo;
import com.dindamaylan.tasku.ui.TaskDetailAct;
import com.dindamaylan.tasku.ui.task.TaskAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuTaskFragment extends Fragment implements OnGetTaskListener{
    private static final String UID_ARG = "user_Id";
    private static final String STATUS_ARG = "status";
    private String currentUserId;
    private FragmentMenuTaskBinding binding;

    public static MenuTaskFragment getInstance(String userId, String status) {
        MenuTaskFragment fragment = new MenuTaskFragment();
        Bundle argument = new Bundle();
        argument.putString(UID_ARG, userId);
        argument.putString(STATUS_ARG, status);
        fragment.setArguments(argument);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMenuTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String userId = getArguments().getString(UID_ARG, "");
            String status = getArguments().getString(STATUS_ARG, "");
            Log.d("TAG", "getargument: "+userId+ " ," + status);
            if (!userId.isEmpty() && !status.isEmpty()) {
                currentUserId = userId;
                getMenuTask(status);
            }
        }
        binding.tabTodo.setOnClickListener(v -> {
            getMenuTask(StatusTask.todo.toString());
        });

        binding.tabDoing.setOnClickListener(v -> {
            getMenuTask(StatusTask.doing.toString());
        });

        binding.tabDone.setOnClickListener(v -> {
            getMenuTask(StatusTask.done.toString());
        });

        binding.tabMissing.setOnClickListener(v -> {
            getMenuTask(StatusTask.missing.toString());
        });
    }

    private void getMenuTask(String status) {
        Log.d("TAG", "getMenuTask: "+status);
        getStatusTask(currentUserId, status);
        //set Button
        setActiveButton(status);
    }

    private void setActiveButton(String status) {
        ArrayList<MaterialButton> buttons = new ArrayList<>(Arrays.asList(binding.tabTodo, binding.tabDoing, binding.tabDone, binding.tabMissing));
        Integer indeks = getIndeksButton(status);
        for (int i = 0; i < buttons.size(); i++) {
            boolean isEnable = i == indeks;
            int textColor;
            if (isEnable) textColor = R.color.white; else textColor = R.color.black;

            int bgColor;
            if (isEnable) bgColor = R.color.tab_enable; else bgColor = R.color.tab_disable;

            buttons.get(i).setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), bgColor));
            buttons.get(i).setTextColor(ContextCompat.getColor(requireContext(), textColor));

        }
    }

    private int getIndeksButton (String status) {
        switch (status) {
            case "doing": return 1;
            case "done": return 2;
            case "missing": return 3;
            default: return 0;
        }
    }

    private void getStatusTask(String user_id, String status) {
        Log.d("TAG", "getStatusTask: "+true);
        new TaskRepo().getTaskByStatusAndUserId(user_id, status, ((isSuccess, listOfTask) -> {
            binding.rvTask.setItemAnimator(new DefaultItemAnimator());
            if (isAdded()){
                Log.d("TAG", "getStatusTask: "+isSuccess+" list : "+listOfTask.size());
                binding.rvTask.setAdapter(new TaskAdapter(this, requireActivity(), listOfTask));
            }
        }));
    }

    @Override
    public void onItemTaskListener(TaskData task) {
        Intent intent = new Intent(requireActivity(), TaskDetailAct.class);
        intent.putExtra(TaskDetailAct.EXTRA_TASK, task);
        startActivity(intent);
    }
}
