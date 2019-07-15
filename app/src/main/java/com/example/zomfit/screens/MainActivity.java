package com.example.zomfit.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.zomfit.R;
import com.example.zomfit.databinding.ActivityMainBinding;
import com.example.zomfit.models.City;
import com.example.zomfit.network.ApiService;
import com.example.zomfit.screens.landing.home.HomeFragment;
import com.example.zomfit.screens.landing.myaccount.MyAccountFragment;
import com.example.zomfit.screens.landing.mybooking.MyBookingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener, MyBookingFragment.OnFragmentInteractionListener,
        MyAccountFragment.OnFragmentInteractionListener {

    ActivityMainBinding binding;
    ActionBar actionBar;
    public static final String BASE_URL = "http://10.0.2.2:3000";
    private static Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        loadFragment(new HomeFragment());
        actionBar = getSupportActionBar();
        setupBottomNavigation();
        connectAndGetApiData();
        loadCities();
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

    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    private void loadCities() {
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<City>> allCities = apiService.getAllCities();
        allCities.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                Log.d("Response1",response.body().get(0).name);
                Log.d("Response2",response.body().get(0).id);
                Log.d("Response3",response.body().get(0).centerIdList.get(0));
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Log.d("error", t.toString());
            }
        });
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