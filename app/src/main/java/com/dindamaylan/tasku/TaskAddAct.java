package com.dindamaylan.tasku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class TaskAddAct extends AppCompatActivity {

    Button btn_back;
    private DatePickerDialog datePickerDialog;
    LinearLayout input_tgl;
    TextView txtTgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);
        initDatePicker();

        //text tgl yang buat nampilin tgl dipilih
        txtTgl = findViewById(R.id.txtTgl);
        //linear layout tanggal yg buat click
        input_tgl = findViewById(R.id.input_tgl);

        //btn kembali
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backTaskTodo = new Intent(TaskAddAct.this, HomeTodoAct.class);
                startActivity(backTaskTodo);
                finish();
            }
        });

        //btn

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month +1;
            String date = makeDateString(dayOfMonth, month, year);
            txtTgl.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.setTitle("Select Date of the Deadline");
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return getMontFormat(month) + " " + dayOfMonth + " " + year;
    }

    private String getMontFormat(int month) {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Okt";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Des";
        //default return
        return "Jan";
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }
}