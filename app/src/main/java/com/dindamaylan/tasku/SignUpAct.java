package com.dindamaylan.tasku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;

public class SignUpAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //scroll view
        ScrollView sv = (ScrollView)findViewById(R.id.scrollView);
        sv.scrollTo(0, sv.getBottom());
    }


}