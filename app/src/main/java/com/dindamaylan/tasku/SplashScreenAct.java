package com.dindamaylan.tasku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenAct extends AppCompatActivity {
    ImageView logo_tasku;

    //animasi
    Animation anim_splash, btt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //load elemen
        logo_tasku = findViewById(R.id.logo_tasku);

        //load animasi
        anim_splash = AnimationUtils.loadAnimation(this, R.anim.anim_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //run animator
        logo_tasku.startAnimation(btt);

        //set timer
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //merubah dari activity default ke activity lain
                Intent goGetStarted = new Intent(SplashScreenAct.this, GetStartedAct.class);
                startActivity(goGetStarted);
                finish();
            }
        }, 1800);

    }
}