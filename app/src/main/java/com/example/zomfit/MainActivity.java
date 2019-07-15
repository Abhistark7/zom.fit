package com.example.zomfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zomfit.databinding.ActivityMainBinding;
import com.example.zomfit.screens.landing.home.HomeFragment;
import com.example.zomfit.screens.landing.myaccount.MyAccountFragment;
import com.example.zomfit.screens.landing.mybooking.MyBookingFragment;
import com.example.zomfit.utils.BasicUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener, MyBookingFragment.OnFragmentInteractionListener,
        MyAccountFragment.OnFragmentInteractionListener {

    ActivityMainBinding binding;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        loadFragment(new HomeFragment());
        actionBar = getSupportActionBar();
        setupBottomNavigation();
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
