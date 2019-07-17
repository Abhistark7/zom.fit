package com.example.zomfit.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.zomfit.R;
import com.example.zomfit.databinding.ActivityMainBinding;
import com.example.zomfit.models.User;
import com.example.zomfit.screens.landing.home.HomeFragment;
import com.example.zomfit.screens.landing.myaccount.MyAccountFragment;
import com.example.zomfit.screens.landing.mybooking.CompletedBookingFragment;
import com.example.zomfit.screens.landing.mybooking.MyBookingFragment;
import com.example.zomfit.screens.landing.mybooking.UpcomingBookingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener, MyBookingFragment.OnFragmentInteractionListener,
        MyAccountFragment.OnFragmentInteractionListener, UpcomingBookingFragment.OnFragmentInteractionListener,
        CompletedBookingFragment.OnFragmentInteractionListener {

    private static final String ARG_USER = "user";
    private static final String MY_BOOKING_FRAGMENT = "my_booking_fragment";
    private static final String BOOK_FRAGMENT = "book_fragment";
    private static final String MY_ACCOUNT_FRAGMENT = "my_account_fragment";
    private static final String ARG_START_FRAGMENT = "arg_fragment";
    private ActivityMainBinding binding;
    private User user;
    private String startFragment;
    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new MyBookingFragment();
    final Fragment fragment3 = new MyAccountFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        binding.navigation.setSelectedItemId(R.id.book_icon);
        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();
        setupBottomNavigation();
        extract();
    }

    private void extract() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            user = Parcels.unwrap(intent.getExtras().getParcelable(ARG_USER));
            startFragment = intent.getExtras().getString(ARG_START_FRAGMENT);
            if (startFragment != null) {
                startSelectedFragment(startFragment);
            }
            if (user != null) {
                saveUserToSharedPreferences(user);
            }
        }
    }

    private void startSelectedFragment(String startFragment) {
        switch (startFragment) {
            case MY_BOOKING_FRAGMENT:
                fm.beginTransaction().hide(active).show(fragment2).commit();
                binding.navigation.setSelectedItemId(R.id.my_bookings_icon);
                active = fragment2;
                break;
            case BOOK_FRAGMENT:
                fm.beginTransaction().hide(active).show(fragment1).commit();
                active = fragment1;
                break;
            case MY_ACCOUNT_FRAGMENT:
                fm.beginTransaction().hide(active).show(fragment3).commit();
                active = fragment3;
                break;
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
        if (user.likedActivitiesList != null && user.savedAddressList != null) {
            Set<String> likedActivitySet = new HashSet<>(user.likedActivitiesList);
            Set<String> savedCenterSet = new HashSet<>(user.savedCenterIdList);
            editor.putStringSet(getString(R.string.user_saved_center), savedCenterSet);
            editor.putStringSet(getString(R.string.user_liked_activity), likedActivitySet);
        }
        editor.apply();
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
        switch (item.getItemId()) {
            case R.id.my_bookings_icon:
                fm.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2;
                return true;
            case R.id.book_icon:
                fm.beginTransaction().hide(active).show(fragment1).commit();
                active = fragment1;
                return true;
            case R.id.my_account_icon:
                fm.beginTransaction().hide(active).show(fragment3).commit();
                active = fragment3;
                return true;
        }
        return false;
    }
}