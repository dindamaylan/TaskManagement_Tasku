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

public class HomeDoingAct extends AppCompatActivity {
    Button tab_todo, tab_done, tab_missing;
    TextView nama, bio;

    DatabaseReference reference;

    String USERNAME_KEY = "username_key";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doing);

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

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key = sharedPreferences.getString(username_key, "");
    }

}