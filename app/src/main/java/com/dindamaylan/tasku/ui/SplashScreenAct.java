package com.dindamaylan.tasku.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dindamaylan.tasku.R;
import com.dindamaylan.tasku.databinding.ActivitySplashScreenBinding;
import com.dindamaylan.tasku.repo.local.LocalStore;
import com.dindamaylan.tasku.ui.home.HomeAct;

public class SplashScreenAct extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;

    //animasi
    Animation anim_splash, btt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //load animasi
        anim_splash = AnimationUtils.loadAnimation(this, R.anim.anim_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //load elemen
        binding.logoTasku.startAnimation(btt);

        //set timer
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            //merubah dari activity default ke activity lain
            LocalStore.getInstance(this).getUserId((isSuccess, user_id) -> {
                Log.d("TAG", "user_id: "+user_id);
                if (isSuccess && !user_id.isEmpty()) {
                    startActivity(new Intent(SplashScreenAct.this, HomeAct.class));
                }
                else {
                    startActivity(new Intent(SplashScreenAct.this, GetStartedAct.class));
                }
                finish();
            });
        }, 1800);

    }
}