package com.notenow.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.notenow.R;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.splashlogo);
        textView = findViewById(R.id.splashText);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anime1);
                imageView.startAnimation(animation);
                textView.startAnimation(animation);
                startActivity(intent);
                finish();

            }
        },3000);
    }
}