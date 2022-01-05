package com.dindamaylan.tasku.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dindamaylan.tasku.data.StatusTask;
import com.dindamaylan.tasku.data.TaskData;
import com.dindamaylan.tasku.databinding.ActivityTaskDetailBinding;
import com.dindamaylan.tasku.repo.remote.TaskRepo;
import com.dindamaylan.tasku.utils.Helpers;

public class TaskDetailAct extends AppCompatActivity {
    public static final String EXTRA_TASK = "extra_task";

    private ActivityTaskDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TaskData intentTask = getIntent().getExtras().getParcelable(EXTRA_TASK);
        if (intentTask != null) {
            binding.tvTittle.setText(intentTask.title);
            binding.tvDesc.setText(intentTask.desc);
            binding.tvDeadline.setText(new Helpers().formatTime(intentTask.deadline));
            binding.tvStatus.setText(intentTask.status);
            if (intentTask.status.equals("doing")) binding.btnHapus.setText("Batal");
            binding.btnAction.setText(getTextActionBtn(intentTask.status));

            binding.btnHapus.setOnClickListener(v ->
                    new TaskRepo().deleteTask(intentTask.id, (isSuccess, message) -> {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        if (isSuccess) onBackPressed();
                    })
            );

            binding.btnAction.setOnClickListener(v -> {
                switch (intentTask.status) {
                    case "todo":
                        updateStatusTask(intentTask.id, StatusTask.doing.toString());
                        break;
                    case "doing":
                        updateStatusTask(intentTask.id, StatusTask.done.toString());
                        break;
                    case "missing":
                        updateTask(intentTask);
                        break;
                    default:
                        createNewTask();
                }
            });
        }

        binding.btnBack.setOnClickListener(view -> finish());

    }

    private void createNewTask() {
        startActivity(new Intent(this, TaskAddAct.class));
        finish();
    }

    private void updateTask(TaskData intentTask) {
        Intent intent = new Intent(this, TaskAddAct.class);
        intent.putExtra(TaskAddAct.EXTRA_UPDATE, intentTask);
        startActivity(intent);
        finish();
    }

    private void updateStatusTask(String id, String newStatus) {
        new TaskRepo().updateStatusTask(id, newStatus, (isSuccess, message) -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (isSuccess) finish();
        });
    }

    private String getTextActionBtn(String status) {
        switch (status) {
            case "todo":
                return "Kerjakan";
            case "doing":
                return "Selesai";
            case "done":
                return "Buat Task Baru";
            case "missing":
                return "Jadwalkan Ulang";
            default:
                return "kerjakan";
        }
    }
}