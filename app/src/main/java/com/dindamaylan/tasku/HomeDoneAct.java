package com.dindamaylan.tasku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeDoneAct extends AppCompatActivity {
    Button tab_todo, tab_doing, tab_missing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_done);
        //tab to_do
        tab_todo = findViewById(R.id.tab_todo);
        tab_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goTodoList = new Intent(HomeDoneAct.this, HomeTodoAct.class);
                startActivity(goTodoList);
                finish();
            }
        });

        //tab doing
        tab_doing = findViewById(R.id.tab_doing);
        tab_doing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goDoingList = new Intent(HomeDoneAct.this, HomeDoingAct.class);
                startActivity(goDoingList);
                finish();
            }
        });

        //tab missing
        tab_missing = findViewById(R.id.tab_missing);
        tab_missing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMissingList = new Intent(HomeDoneAct.this, HomeMissingAct.class);
                startActivity(goMissingList);
                finish();
            }
        });
    }
}