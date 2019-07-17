package com.example.zomfit.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.zomfit.R;
import com.example.zomfit.databinding.ActivityCentersListingBinding;
import com.example.zomfit.databinding.ActivitySavedCenterBinding;
import com.example.zomfit.models.Center;
import com.example.zomfit.models.getsavedcenter.GetSavedCenterRequest;
import com.example.zomfit.models.getsavedcenter.GetSavedCenterResponse;
import com.example.zomfit.network.ApiService;
import com.example.zomfit.screens.center.CenterActivity;
import com.example.zomfit.screens.centerlisting.CenterListingAdapter;
import com.example.zomfit.screens.centerlisting.CentersListing;
import com.example.zomfit.utils.BasicUtils;

import org.parceler.Parcels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SavedCenterActivity extends AppCompatActivity {

    private static final String ARG_CENTER = "center";
    private ActivitySavedCenterBinding binding;
    private CenterListingAdapter adapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_saved_center);
        initialize();
    }

    private void initialize() {
        retrofit = BasicUtils.connectApi();
        showLoadingView();
        setUpCenterRecyclerView();
        setToolbarTitle(getString(R.string.saved_centers_label));
        getSavedCenter();
    }

    private void setUpCenterRecyclerView() {
        adapter = new CenterListingAdapter(this, center -> openCenterActivity(center));
        binding.centersRecycler.setNestedScrollingEnabled(false);
        binding.centersRecycler.setAdapter(adapter);
        binding.centersRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setToolbarTitle(String title) {
        binding.toolbar.toolbarTitle.setText(title);
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
    }

    private void getSavedCenter() {
        GetSavedCenterRequest request = new GetSavedCenterRequest();
        request.userId = getUserId();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getSavedCenter(request).enqueue(new Callback<GetSavedCenterResponse>() {
            @Override
            public void onResponse(Call<GetSavedCenterResponse> call, Response<GetSavedCenterResponse> response) {
                showContentView();
                adapter.update(response.body().centerList);
            }

            @Override
            public void onFailure(Call<GetSavedCenterResponse> call, Throwable t) {
                showErrorView();
            }
        });
    }

    private String getUserId() {
        SharedPreferences sharedPref = SavedCenterActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(getString(R.string.user_id_label), "");
    }

    private void openCenterActivity(Center center) {
        Intent intent = new Intent(SavedCenterActivity.this, CenterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CENTER, Parcels.wrap(center));
        intent.putExtras(bundle);
        startActivity(intent);
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
