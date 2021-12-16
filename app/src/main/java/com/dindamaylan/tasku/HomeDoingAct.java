package com.dindamaylan.tasku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeDoingAct extends AppCompatActivity {
    Button tab_todo, tab_done, tab_missing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doing);

        //tab to_do
        tab_todo = findViewById(R.id.tab_todo);
        tab_todo.setOnClickListener(view -> {
            Intent goTodoList = new Intent(HomeDoingAct.this, HomeTodoAct.class);
            startActivity(goTodoList);
            finish();
        });

        //tab done
        tab_done = findViewById(R.id.tab_done);
        tab_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goListDone = new Intent(HomeDoingAct.this, HomeDoneAct.class);
                startActivity(goListDone);
                finish();
            }
        });

        //tab missing
        tab_missing = findViewById(R.id.tab_missing);
        tab_missing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMissingList = new Intent(HomeDoingAct.this, HomeMissingAct.class);
                startActivity(goMissingList);
                finish();
            }
        });
    }
}