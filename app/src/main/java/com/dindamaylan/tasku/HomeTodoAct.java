package com.dindamaylan.tasku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeTodoAct extends AppCompatActivity {

    Button tab_doing, tab_done, tab_missing;
    FloatingActionButton fab_taskAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_todo);

        //tab menu doing
        tab_doing = findViewById(R.id.tab_doing);
        tab_doing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDoing  = new Intent(HomeTodoAct.this, HomeDoingAct.class);
                startActivity(toDoing);
                finish();
            }
        });

        //tab menu done
        tab_done = findViewById(R.id.tab_done);
        tab_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDone = new Intent(HomeTodoAct.this, HomeDoneAct.class);
                startActivity(toDone);
                finish();
            }
        });

        //tab menu missing
        tab_missing = findViewById(R.id.tab_missing);
        tab_missing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMissing = new Intent(HomeTodoAct.this, HomeMissingAct.class);
                startActivity(toMissing);
                finish();
            }
        });

        //button add task
//        fab_taskAdd.findViewById(R.id.fab_taskAdd);
//        fab_taskAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toAddTask = new Intent(HomeTodoAct.this, TaskAddAct.class);
//                startActivity(toAddTask);
//                finish();
//            }
//        });
    }

}