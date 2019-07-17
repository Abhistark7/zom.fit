package com.example.zomfit.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;

import com.example.zomfit.R;
import com.example.zomfit.databinding.ActivitySplashBinding;
import com.example.zomfit.models.User;
import com.example.zomfit.screens.landing.LoginActivity;

import org.parceler.Parcels;

public class SplashActivity extends AppCompatActivity {

    private static final String ARG_USER = "user";
    private static final int LAUNCH_DELAY = 1000;
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        isLoggedIn();
    }

    private void isLoggedIn() {
        SharedPreferences sharedPref = SplashActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean(getString(R.string.is_logged_in_label), false);
        if(isLoggedIn) {
            startMainActivity();
        } else {
            startLoginActivity();
        }
    }

    private void startMainActivity() {
        final Runnable r = () -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        };
        Handler handler = new Handler();
        handler.postDelayed(r, LAUNCH_DELAY);
    }

    private void startLoginActivity() {
        final Runnable r = () -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        };
        Handler handler = new Handler();
        handler.postDelayed(r, LAUNCH_DELAY);
    }
}
