package com.dindamaylan.tasku.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dindamaylan.tasku.data.StatusTask;
import com.dindamaylan.tasku.data.TaskData;
import com.dindamaylan.tasku.databinding.ActivityTaskAddBinding;
import com.dindamaylan.tasku.repo.local.LocalStore;
import com.dindamaylan.tasku.repo.remote.TaskRepo;
import com.dindamaylan.tasku.utils.Helpers;
import com.google.firebase.Timestamp;

import java.util.Calendar;

public class TaskAddAct extends AppCompatActivity {

    public static final String EXTRA_UPDATE = "extra_update";
    private DatePickerDialog datePickerDialog;
    private ActivityTaskAddBinding binding;
    private Timestamp selectedDate;
    private boolean isUpdate = false;
    private String idTaskUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            TaskData intentTask = getIntent().getExtras().getParcelable(EXTRA_UPDATE);
            binding.tvHeader.setText("Jadwalkan Ulang");
            isUpdate = true;
            idTaskUpdate = intentTask.id;
            fillUpdateData(intentTask);
        }

        binding.btnBack.setOnClickListener(v -> finish());
        binding.llTgl.setOnClickListener(v -> initDatePicker());
        binding.btnSimpan.setOnClickListener(v -> {
            LocalStore.getInstance(this).getUserId(((isSuccess, userId) -> {
                if (isSuccess && !userId.isEmpty()) {
                    if (isUpdate) {
                        updateTask(userId);
                    } else {
                        addTask(userId);
                    }

                    Log.d("TAG", "userId: " + userId);


                }
            }));
        });
    }

    private void addTask(String userId) {
        TaskData taskData = new TaskData(
                StatusTask.todo, selectedDate,
                binding.etJudul.getText().toString(),
                binding.etDesc.getText().toString(),
                userId
        );
        new TaskRepo().addNewTask(taskData, ((isSuccessRepo, message) -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (isSuccessRepo) finish();
        }));
    }

    private void updateTask(String userId) {
        TaskData taskData = new TaskData(
                StatusTask.todo, selectedDate,
                binding.etJudul.getText().toString(),
                binding.etDesc.getText().toString(),
                userId, idTaskUpdate
        );
        new TaskRepo().updateTask(taskData, ((isSuccess, message) -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (isSuccess) finish();
        }));
    }

    private void fillUpdateData(TaskData intentTask) {
        binding.etJudul.setText(intentTask.title);
        binding.etDesc.setText(intentTask.desc);
        binding.tvTgl.setText(new Helpers().formatTime(intentTask.deadline));
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            Calendar myCalendar = Calendar.getInstance();
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Timestamp timestamp = new Timestamp(myCalendar.getTime());
            selectedDate = timestamp;
            binding.tvTgl.setText(new Helpers().formatTime(timestamp));
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.setTitle("Select Date of the Deadline");
        datePickerDialog.show();
    }
}