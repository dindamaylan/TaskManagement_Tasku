package com.dindamaylan.tasku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class HomeAct extends AppCompatActivity {
    ScrollView scrollView;
    LinearLayout to_do, doing, done, missing, card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //to_do
        to_do = findViewById(R.id.to_do);
        to_do.setOnClickListener(view -> {
            Intent goTodoList = new Intent(HomeAct.this, HomeTodoAct.class);
            startActivity(goTodoList);
        });


        //doing
        doing = findViewById(R.id.doing);
        doing.setOnClickListener(view -> {
            Intent toHomeDoing = new Intent(HomeAct.this, HomeDoingAct.class);
            startActivity(toHomeDoing);

        });

        //done
        done = findViewById(R.id.done);
        done.setOnClickListener(view -> {
            Intent toHomeDone = new Intent(HomeAct.this, HomeDoneAct.class);
            startActivity(toHomeDone);

        });

        //missing
        missing = findViewById(R.id.missing);
        missing.setOnClickListener(view -> {
            Intent toHomeMissing = new Intent(HomeAct.this, HomeMissingAct.class);
            startActivity(toHomeMissing);

        });

        //card
        card = findViewById(R.id.card);
        card.setOnClickListener(view -> {
            Intent toCardDetail = new Intent(HomeAct.this, TaskDetailAct.class);
            startActivity(toCardDetail);
            finish();
        });


        //scroll view
        scrollView = findViewById(R.id.scrollView);
        scrollView.scrollTo(0, scrollView.getBottom());
    }
}