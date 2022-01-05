package com.dindamaylan.tasku.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.dindamaylan.tasku.data.StatusTask;
import com.dindamaylan.tasku.data.TaskData;
import com.dindamaylan.tasku.databinding.FragmentHomeBinding;
import com.dindamaylan.tasku.repo.remote.TaskRepo;
import com.dindamaylan.tasku.ui.TaskDetailAct;
import com.dindamaylan.tasku.ui.task.TaskAdapter;
import com.dindamaylan.tasku.utils.DataHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements OnGetTaskListener {
    private FragmentHomeBinding binding;
    private static final String UID_ARG = "user_Id";
    private final TaskMenuAdapter.MenuTaskListener listener;

    public static Fragment newInstance(TaskMenuAdapter.MenuTaskListener listener, String userId) {
        HomeFragment fragment = new HomeFragment(listener);
        Bundle argument = new Bundle();
        argument.putString(UID_ARG, userId);
        fragment.setArguments(argument);
        return fragment;
    }

    public HomeFragment(TaskMenuAdapter.MenuTaskListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    //setelah viewnya dibuat
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String user_id = getArguments().getString(UID_ARG, "");
            if (!user_id.isEmpty()) {
                getAllTask(user_id);
                getMenuTask(user_id);
            }
        }
    }

    private void getAllTask(String user_id) {
        new TaskRepo().getDeadlineTask(user_id, ((isTaskSuccess, listOfTask) -> {
            if (isTaskSuccess) {
                binding.rvDeadlineTask.setItemAnimator(new DefaultItemAnimator());
                if (isAdded()) {
                    Log.d("TAG", "getAllTask: " + listOfTask);
                    binding.rvDeadlineTask.setAdapter(new TaskAdapter(this, requireActivity(), new ArrayList<>(getDeadlineTask(listOfTask))));
                }
            }
        }));
    }

    private void getMenuTask(String userId) {
        new TaskRepo().getTaskByUserId(userId, (isSuccess, listOfTask) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<TaskData> todoTask = getTodoTask(listOfTask);
                List<TaskData> doingTask = getDoingTask(listOfTask);
                List<TaskData> doneTask = getDoneTask(listOfTask);
                List<TaskData> missingTask = getMissingTask(listOfTask);
                binding.rvTaskMenu.setItemAnimator(new DefaultItemAnimator());
                if (isAdded()) {
                    binding.rvTaskMenu.setAdapter(new TaskMenuAdapter(
                            requireActivity(), listener, new DataHelpers().getMenuTask(
                            todoTask.size(), doingTask.size(), doneTask.size(), missingTask.size()
                    )));
                }
            }
        });
    }

    @SuppressLint("NewApi")
    private List<TaskData> getTodoTask(ArrayList<TaskData> listOfTask) {
        return listOfTask.stream()
                .filter(taskData -> taskData.status.equals(StatusTask.todo.toString()))
                .collect(Collectors.toList());
    }

    @SuppressLint("NewApi")
    private List<TaskData> getDoingTask(ArrayList<TaskData> listOfTask) {
        return listOfTask.stream()
                .filter(taskData -> taskData.status.equals(StatusTask.doing.toString()))
                .collect(Collectors.toList());
    }

    @SuppressLint("NewApi")
    private List<TaskData> getDoneTask(ArrayList<TaskData> listOfTask) {
        return listOfTask.stream()
                .filter(taskData -> taskData.status.equals(StatusTask.done.toString()))
                .collect(Collectors.toList());
    }

    @SuppressLint("NewApi")
    private List<TaskData> getMissingTask(ArrayList<TaskData> listOfTask) {
        return listOfTask.stream()
                .filter(taskData -> taskData.status.equals(StatusTask.missing.toString()))
                .collect(Collectors.toList());
    }

    @SuppressLint("NewApi")
    private List<TaskData> getDeadlineTask(ArrayList<TaskData> listOfTask) {
        return listOfTask.stream()
                .filter(taskData -> !taskData.status.equals(StatusTask.done.toString()))
                .collect(Collectors.toList());
    }

    @Override
    public void onItemTaskListener(TaskData task) {
        Intent intent = new Intent(requireActivity(), TaskDetailAct.class);
        intent.putExtra(TaskDetailAct.EXTRA_TASK, task);
        startActivity(intent);
    }
}
