package com.example.zomfit.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.zomfit.R;
import com.example.zomfit.databinding.ActivityBookNowBinding;
import com.example.zomfit.models.activity.Activity;
import com.example.zomfit.models.book.BaseResponse;
import com.example.zomfit.models.book.BookActivityRequest;
import com.example.zomfit.network.ApiService;
import com.example.zomfit.utils.BasicUtils;
import com.example.zomfit.utils.ImageUtils;

import org.parceler.Parcels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookNow extends AppCompatActivity {

    private static final String ARG_ACTIVITY_NAME = "arg_activity";
    private static final String ARG_ACTIVITY_TIME = "arg_time";
    private static final String ARG_ACTIVITY_DATE = "arg_date";
    private static final String ARG_CENTER_NAME = "arg_center";
    private static final String ARG_CITY_NAME = "arg_city_name";
    private static final String ARG_CENTER_URL = "arg_center_url";
    private static final String ARG_ACTIVITY_URL = "arg_activity_url";
    private String activityName;
    private String activityTime;
    private String activityDate;
    private String centerName;
    private String cityName;
    private String centerUrl;
    private String activityUrl;
    private ActivityBookNowBinding binding;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_now);
        extract();
        initialize();
    }

    private void extract() {
        Intent intent = getIntent();
        activityName = intent.getExtras().getString(ARG_ACTIVITY_NAME);
        activityTime = intent.getExtras().getString(ARG_ACTIVITY_TIME);
        activityDate = intent.getExtras().getString(ARG_ACTIVITY_DATE);
        centerName = intent.getExtras().getString(ARG_CENTER_NAME);
        cityName = intent.getExtras().getString(ARG_CITY_NAME);
        centerUrl = intent.getExtras().getString(ARG_CENTER_URL);
        activityUrl = intent.getExtras().getString(ARG_ACTIVITY_URL);
    }

    private void initialize() {
        setUpToolbar();
        setUpStaticViews();
        handleButtonClick();
    }

    private void setUpToolbar() {
        binding.toolbar.toolbarTitle.setText(getString(R.string.booking_details));
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
    }

    private void setUpStaticViews() {
        Glide.with(this).load(centerUrl).into(binding.bookNowView.centerImage);
        ImageUtils.fetchSvg(this, activityUrl, binding.bookNowView.activityImage);
        binding.bookNowView.activityName.setText(activityName);
        binding.bookNowView.timing.setText(String.format("%s, %s", activityTime, activityDate));
        binding.bookNowView.location.setText(String.format("%s, %s", centerName, cityName));
    }

    private void handleButtonClick() {
        binding.bookButton.setOnClickListener(v -> bookActivity());
    }

    private void bookActivity() {
        showProgressBar(true);
        retrofit = BasicUtils.connectApi();
        ApiService apiService = retrofit.create(ApiService.class);
        BookActivityRequest request = new BookActivityRequest();
        request.activityIconUrl = activityUrl;
        request.activityName = activityName;
        request.centerImageUrl = centerUrl;
        request.centerName = centerName;
        request.cityName = cityName;
        request.date = activityDate;
        request.time = activityTime;
        request.userId = getUserId();
        Call<BaseResponse> responseCall = apiService.bookActivity(request);
        responseCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                showProgressBar(false);
                if(response.body().status) {
                    BasicUtils.makeToast(BookNow.this, "Activity booked!");
                    openMainActivity();
                } else {
                    BasicUtils.makeToast(BookNow.this, "An error occurred");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                showProgressBar(false);
                BasicUtils.makeToast(BookNow.this, "An error occurred");
            }
        });
    }

    private void openMainActivity() {
        Intent intent = new Intent(BookNow.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private String getUserId() {
        SharedPreferences sharedPref = BookNow.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(getString(R.string.user_id_label), "");
    }

    private void showProgressBar(boolean show) {
        if(show) {
            binding.progressCircular.setVisibility(View.VISIBLE);
        } else {
            binding.progressCircular.setVisibility(View.GONE);
        }
    }
}
