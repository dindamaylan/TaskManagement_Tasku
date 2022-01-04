package com.dindamaylan.tasku.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dindamaylan.tasku.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeMissingAct extends AppCompatActivity {
    Button tab_todo, tab_doing, tab_done;
    TextView nama, bio;

    DatabaseReference reference;

    String USERNAME_KEY = "username_key";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_missing);

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        String status_task_new = bundle.getString("status_task");

        //mengambil username di local gadget pengguna
        getUsernameLocal();

        //
        nama = findViewById(R.id.nama);
        bio = findViewById(R.id.bio);

        reference = FirebaseDatabase.getInstance().getReference().child("user").child(username_key);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nama.setText("Hi, "+snapshot.child("nama").getValue().toString());
                bio.setText(snapshot.child("bio").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key = sharedPreferences.getString(username_key, "");
    }
}