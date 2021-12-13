package com.dindamaylan.tasku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeDoingAct extends AppCompatActivity {
    Button tab_todo, tab_done, tab_missing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doing);

        //todo
        tab_todo = findViewById(R.id.tab_todo);
        tab_todo.setOnClickListener(view -> {
            Intent goTodoList = new Intent(HomeDoingAct.this, HomeTodoAct.class);
            startActivity(goTodoList);
            finish();
        });

    }
}