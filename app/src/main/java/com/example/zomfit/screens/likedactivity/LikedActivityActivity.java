package com.example.zomfit.screens.likedactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.zomfit.R;
import com.example.zomfit.databinding.ActivityLikedActivityBinding;
import com.example.zomfit.models.getlikedactivity.GetLikedActivityRequest;
import com.example.zomfit.models.getlikedactivity.GetLikedActivityResponse;
import com.example.zomfit.network.ApiService;
import com.example.zomfit.utils.BasicUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LikedActivityActivity extends AppCompatActivity {

    private ActivityLikedActivityBinding binding;
    private LikedActivityAdapter adapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_liked_activity);
        initialize();
    }

    private void initialize() {
        retrofit = BasicUtils.connectApi();
        showLoadingView();
        setUpCenterRecyclerView();
        setToolbarTitle("Liked Activities");
        getSavedCenter();
    }

    private void setUpCenterRecyclerView() {
        adapter = new LikedActivityAdapter(this);
        binding.centersRecycler.setNestedScrollingEnabled(false);
        binding.centersRecycler.setAdapter(adapter);
        binding.centersRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setToolbarTitle(String title) {
        binding.toolbar.toolbarTitle.setText(title);
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
    }

    private void getSavedCenter() {
        GetLikedActivityRequest request = new GetLikedActivityRequest();
        request.userId = getUserId();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getLikedActivity(request).enqueue(new Callback<GetLikedActivityResponse>() {
            @Override
            public void onResponse(Call<GetLikedActivityResponse> call, Response<GetLikedActivityResponse> response) {
                showContentView();
                adapter.update(response.body().activityList);
            }

            @Override
            public void onFailure(Call<GetLikedActivityResponse> call, Throwable t) {
                showErrorView();
            }
        });
    }

    private String getUserId() {
        SharedPreferences sharedPref = LikedActivityActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(getString(R.string.user_id_label), "");
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
