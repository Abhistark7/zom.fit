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
            User user = new User();
            user.userId = sharedPref.getString(getString(R.string.user_id_label), "");
            user.email = sharedPref.getString(getString(R.string.user_email_label), "");
            user.name = sharedPref.getString(getString(R.string.user_name_label), "");
            user.password = sharedPref.getString(getString(R.string.user_password_label), "");
            startMainActivity(user);
        } else {
            startLoginActivity();
        }
    }

    private void startMainActivity(User user) {
        final Runnable r = () -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(ARG_USER, Parcels.wrap(user));
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        };
        Handler handler = new Handler();
        handler.postDelayed(r, 1000);
    }

    private void startLoginActivity() {
        final Runnable r = () -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        };
        Handler handler = new Handler();
        handler.postDelayed(r, 1000);
    }
}
