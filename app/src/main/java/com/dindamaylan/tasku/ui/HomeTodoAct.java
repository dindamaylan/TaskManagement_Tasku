package com.dindamaylan.tasku.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dindamaylan.tasku.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeTodoAct extends AppCompatActivity {

    Button tab_doing, tab_done, tab_missing;
    FloatingActionButton btn_tambah_task;
    TextView nama, bio;

    private RecyclerView recyclerView;


    DatabaseReference reference;

    String USERNAME_KEY = "username_key";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_todo);
        btn_tambah_task = findViewById(R.id.btn_tambah_task);
        nama = findViewById(R.id.nama);
        bio = findViewById(R.id.bio);
        recyclerView = findViewById(R.id.recycleView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        //masih bermasalah
        //button add task
        btn_tambah_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddTask = new Intent(HomeTodoAct.this, TaskAddAct.class);
                startActivity(toAddTask);
                finish();
            }
        });

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String status_task_new = bundle.getString("status_task");
        }

        //mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase. getInstance().getReference().child("user").child("task").child("status_task");
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        //mengambil username di local gadget pengguna
//        getUsernameLocal();

//        reference = FirebaseDatabase.getInstance().getReference().child("user").child(username_key);
//
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                nama.setText("Hi, "+snapshot.child("nama").getValue().toString());
//                bio.setText(snapshot.child("bio").getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

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
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key = sharedPreferences.getString(username_key, "");
    }


}