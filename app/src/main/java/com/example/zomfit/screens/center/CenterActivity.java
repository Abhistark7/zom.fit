package com.example.zomfit.screens.center;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.zomfit.R;
import com.example.zomfit.databinding.ActivityCenterBinding;
import com.example.zomfit.models.Center;
import com.example.zomfit.models.activity.Activity;
import com.example.zomfit.models.activity.Timing;
import com.example.zomfit.models.book.BaseResponse;
import com.example.zomfit.models.getactivities.GetActivitiesRequest;
import com.example.zomfit.models.getactivities.GetActivitiesResponse;
import com.example.zomfit.models.like.LikeActivityRequest;
import com.example.zomfit.models.save.SaveCenterRequest;
import com.example.zomfit.network.ApiService;
import com.example.zomfit.screens.BookNow;
import com.example.zomfit.utils.BasicUtils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CenterActivity extends AppCompatActivity {

    private static final String ARG_CENTER = "center";
    private static final String ARG_ACTIVITY_NAME = "arg_activity";
    private static final String ARG_ACTIVITY_TIME = "arg_time";
    private static final String ARG_ACTIVITY_DATE = "arg_date";
    private static final String ARG_CENTER_NAME = "arg_center";
    private static final String ARG_CENTER_URL = "arg_center_url";
    private static final String ARG_ACTIVITY_URL = "arg_activity_url";
    private static final String ARG_CITY_NAME = "arg_city_name";
    private static final String ARG_ACTIVITY_ID = "arg_activity_id";
    private static final String ARG_TIMING_ID = "arg_timing_id";
    private static final String RETROFIT = "retrofit";
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private ActivityCenterBinding binding;
    private Retrofit retrofit;
    private ActivityAdapter adapter;
    private Center center;
    private List<String> savedCenters = new ArrayList<>();
    private List<String> likedActivity = new ArrayList<>();
    private boolean isBookmarked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_center);
        initialize();
    }

    private void initialize() {
        retrofit = BasicUtils.connectApi();
        setupActivityRecyclerView();
        showLoadingView();
        extract();
        setupBackButton();
    }

    private void extract() {
        Intent intent = getIntent();
        center = Parcels.unwrap(intent.getExtras().getParcelable(ARG_CENTER));
        getAllActivities(center.activityIdList);
        setUpStaticViews();
    }

    private void setUpStaticViews() {
        Glide.with(this).load(center.imageUrl).into(binding.centerImage);
        binding.centerText.setText(center.name);
        binding.cityText.setText(center.cityName);
        binding.ratingValue.setText(center.rating);
        handleBookmarkButton();
    }

    private void setupActivityRecyclerView() {
        adapter = new ActivityAdapter(this, (timing, activity) ->
                CenterActivity.this.openBookNow(timing, activity), getUserId(), new LikeClickHandler() {
            @Override
            public void like(String userId, String activityId) {
                likeActivity(userId, activityId);
            }

            @Override
            public void unlike(String userId, String activityId) {
                unlikeActivity(userId, activityId);
            }
        });
        binding.activityRecycler.setNestedScrollingEnabled(true);
        binding.activityRecycler.setAdapter(adapter);
        binding.activityRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupBackButton() {
        binding.back.setOnClickListener(v -> onBackPressed());
    }

    private void getAllActivities(List<String> activityIdList) {
        ApiService apiService = retrofit.create(ApiService.class);
        GetActivitiesRequest getActivitiesRequest = new GetActivitiesRequest();
        getActivitiesRequest.activityIdList = activityIdList;
        Call<GetActivitiesResponse> responseCall = apiService.getActivityById(getActivitiesRequest);
        responseCall.enqueue(new Callback<GetActivitiesResponse>() {
            @Override
            public void onResponse(Call<GetActivitiesResponse> call, Response<GetActivitiesResponse> response) {
                adapter.update(response.body().activityList);
                showContentView();
            }

            @Override
            public void onFailure(Call<GetActivitiesResponse> call, Throwable t) {
                showErrorView();
                Log.d("error", t.toString());
            }
        });
    }

    private void openBookNow(Timing timing, Activity activity) {
        Intent intent = new Intent(CenterActivity.this, BookNow.class);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_CENTER_NAME, center.name);
        bundle.putString(ARG_CITY_NAME, center.cityName);
        bundle.putString(ARG_ACTIVITY_NAME, activity.name);
        bundle.putString(ARG_ACTIVITY_TIME, timing.time);
        bundle.putString(ARG_ACTIVITY_DATE, timing.date);
        bundle.putString(ARG_ACTIVITY_URL, activity.iconUrl);
        bundle.putString(ARG_CENTER_URL, center.imageUrl);
        bundle.putString(ARG_ACTIVITY_ID, activity.activityId);
        bundle.putString(ARG_TIMING_ID, timing.timingId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void handleBookmarkButton() {
        isBookmarked = center.likedUserids.contains(getUserId());
        if (isBookmarked) {
            binding.bookmark.setImageDrawable(
                    binding.getRoot().getContext().getDrawable(R.drawable.ic_bookmark_filled));
        } else {
            binding.bookmark.setImageDrawable(
                    binding.getRoot().getContext().getDrawable(R.drawable.ic_bookmark_empty));
        }
        binding.bookmark.setOnClickListener(v -> {
            if (isBookmarked) {
                binding.bookmark.setImageDrawable(
                        binding.getRoot().getContext().getDrawable(R.drawable.ic_bookmark_empty));
                unSaveCenter(getUserId(), center.id);
                isBookmarked = false;
            } else {
                binding.bookmark.setImageDrawable(
                        binding.getRoot().getContext().getDrawable(R.drawable.ic_bookmark_filled));
                saveCenter(getUserId(), center.id);
                isBookmarked = true;
            }
        });
    }

    private String getUserId() {
        SharedPreferences sharedPref = CenterActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(getString(R.string.user_id_label), "");
    }

    private void likeActivity(String userId, String activityId) {
        LikeActivityRequest request = new LikeActivityRequest();
        request.activityId = activityId;
        request.userId = userId;
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.likeActivity(request).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().status) {
                    Log.d(RETROFIT, SUCCESS);
                } else {
                    Log.d(RETROFIT, FAILURE);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d(RETROFIT, FAILURE);
            }
        });
    }

    private void unlikeActivity(String userId, String activityId) {
        LikeActivityRequest request = new LikeActivityRequest();
        request.activityId = activityId;
        request.userId = userId;
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.unlikeActivity(request).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().status) {
                    Log.d(RETROFIT, SUCCESS);
                } else {
                    Log.d(RETROFIT, FAILURE);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d(RETROFIT, FAILURE);
            }
        });
    }

    private void saveCenter(String userId, String centerId) {
        SaveCenterRequest request = new SaveCenterRequest();
        request.centerId = centerId;
        request.userId = userId;
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.saveCenter(request).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().status) {
                    Log.d(RETROFIT, SUCCESS);
                } else {
                    Log.d(RETROFIT, FAILURE);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d(RETROFIT, FAILURE);
            }
        });
    }

    private void unSaveCenter(String userId, String centerId) {
        SaveCenterRequest request = new SaveCenterRequest();
        request.centerId = centerId;
        request.userId = userId;
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.unSaveCenter(request).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().status) {
                    Log.d(RETROFIT, SUCCESS);
                } else {
                    Log.d(RETROFIT, FAILURE);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d(RETROFIT, FAILURE);
            }
        });
    }

    private void showLoadingView() {
        binding.contentView.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.VISIBLE);
    }

    private void showErrorView() {
        binding.contentView.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.VISIBLE);
        binding.loadingView.setVisibility(View.GONE);
    }

    private void showContentView() {
        binding.contentView.setVisibility(View.VISIBLE);
        binding.errorView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.GONE);
    }
}
