package com.dindamaylan.tasku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeMissingAct extends AppCompatActivity {
    Button tab_todo, tab_doing, tab_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_missing);

        //tab to_do
        tab_todo = findViewById(R.id.tab_todo);
        tab_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goTodoList = new Intent(HomeMissingAct.this, HomeTodoAct.class);
                startActivity(goTodoList);
                finish();
            }
        });

        //tab doing
        tab_doing = findViewById(R.id.tab_doing);
        tab_doing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goDoingList = new Intent(HomeMissingAct.this, HomeDoingAct.class);
                startActivity(goDoingList);
                finish();
            }
        });

        //tab done
        tab_done = findViewById(R.id.tab_done);
        tab_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMissingList = new Intent(HomeMissingAct.this, HomeDoneAct.class);
                startActivity(goMissingList);
                finish();
            }
        });
    }
}