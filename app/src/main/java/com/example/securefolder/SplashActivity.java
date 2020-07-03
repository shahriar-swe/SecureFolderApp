package com.example.securefolder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static int splash_screen = 5000;

    Animation topAnimation,bottomAnimation;
    ImageView imageView;
    TextView logoTextView,sloganTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageView = findViewById(R.id.splashImageViewId);
        logoTextView = findViewById(R.id.splashLogoTextViewId);
        sloganTextView = findViewById(R.id.splashSloganTextViewId);

        imageView.setAnimation(topAnimation);
        logoTextView.setAnimation(bottomAnimation);
        sloganTextView.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Login_form.class));
                finish();
            }
        },splash_screen);
    }
}
