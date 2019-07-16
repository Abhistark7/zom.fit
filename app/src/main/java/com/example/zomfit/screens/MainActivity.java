package com.example.zomfit.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.zomfit.R;
import com.example.zomfit.databinding.ActivityMainBinding;
import com.example.zomfit.models.City;
import com.example.zomfit.models.User;
import com.example.zomfit.network.ApiService;
import com.example.zomfit.screens.landing.home.HomeFragment;
import com.example.zomfit.screens.landing.myaccount.MyAccountFragment;
import com.example.zomfit.screens.landing.mybooking.MyBookingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener, MyBookingFragment.OnFragmentInteractionListener,
        MyAccountFragment.OnFragmentInteractionListener {

    private static final String ARG_USER = "user";
    private ActivityMainBinding binding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        extract();
        loadFragment(new HomeFragment());
        setupBottomNavigation();
    }

    private void extract() {
        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            user = Parcels.unwrap(intent.getExtras().getParcelable(ARG_USER));
            Log.d("user logged in", user.name);
            saveUserToSharedPreferences(user);
        }
    }

    private void saveUserToSharedPreferences(User user) {
        SharedPreferences sharedPref = MainActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.is_logged_in_label), true);
        editor.putString(getString(R.string.user_id_label), user.userId);
        editor.putString(getString(R.string.user_email_label), user.email);
        editor.putString(getString(R.string.user_name_label), user.name);
        editor.putString(getString(R.string.user_password_label), user.password);
        editor.apply();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = binding.navigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.my_bookings_icon:
                fragment = new MyBookingFragment();
                break;
            case R.id.book_icon:
                fragment = new HomeFragment();
                break;
            case R.id.my_account_icon:
                fragment = new MyAccountFragment();
                break;
        }
        loadFragment(fragment);
        return true;
    }
}